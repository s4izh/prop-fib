package domain.controllers;

import domain.classes.Document;
import domain.classes.DocumentData;
import domain.classes.DummyData;
import domain.classes.ResultDocuments;

import persistence.PersistenceController;

import java.io.*;
import java.util.*;

public class DomainController implements Serializable {

    /**
     * Controlador de la classe Author.
     */
    private AuthorController authorController;

    /**
     * Controlador de la classe BooleanExpression.
     */
    private BooleanController booleanController;

    /**
     * Controlador de la classe Document.
     */
    private DocumentController documentController;

    /**
     * Instància de la classe ResultDocuments.
     */
    private ResultDocuments resultDocuments;

    /**
     * Instància de la classe PersistenceController.
     */
    private PersistenceController persistenceController;

    /**
     * Instància del controlador de domini.
     */
    private static DomainController singletonObject;

    /**
     * Constructura del DomainController.
     */
    public DomainController() {
    }

    /**
     * Inicialització del DomainController, els controladors i el ResultDocuments.
     */
    public void iniDomainController() {
        resultDocuments = new ResultDocuments();
        persistenceController = PersistenceController.getInstance();
        persistenceController.iniPersistenceController();
        authorController = new AuthorController();
        booleanController = new BooleanController();
        documentController = new DocumentController();
    }

    /**
     * Funció per retornar l'instància del DomainController.
     * @return Una instància del DomainController.
     */
    public static DomainController getInstance() {
        if (singletonObject == null) {
            singletonObject = new DomainController();
        }
        return singletonObject;
    }

    /**
     * Funció per saber si existeix un document.
     * @param title Títol d'un document.
     * @param author Autor d'un document.
     * @return Un boolean amb valor true si existeix el document, false en cas contrari.
     */
    public boolean existsDocument(String title, String author) {
        return documentController.existsDocument(title, author);
    }

    /**
     * Funció per afegir un nou document al sistema i crea l'autor si aquest no existia.
     * @param title   títol del Document.
     * @param author  autor del Document.
     * @param content contingut del Document.
     * @throws Exception es llença una excepció en cas que ja existeixi un Document amb el mateix títol i autor.
     */
    public void addDocument(String title, String author, String content) throws Exception {
        if (documentController.existsDocument(title, author))
            throw new Exception("El document amb títol (" + title + ") i autor (" + author + ") ja existeix.");
        documentController.addDocument(title, author, content);
        Document d = documentController.getDocument(title, author);
        if (authorController.authorExists(author)) authorController.addDocument(author, d);
        else authorController.addAuthor(author, d);

        saveIndividualDocumentMetadata(title,author);
    }

    /**
     * Funció per eliminar un document del sistema. Una vegada eliminat si el nombre de documents creat per l'autor al sistema es igual a 0, aquest s'elimina del sistema.
     * @param title  títol del Document.
     * @param author autor del Document.
     * @throws Exception es llença una excepció en cas que no existeixi el Document identificat pel títol i l'autor.
     */
    public void deleteDocument(String title, String author) throws Exception {
        if (!documentController.existsDocument(title, author))
            throw new Exception("El document amb títol (" + title + ") i autor (" + author + ") no existeix.");
        Document d = documentController.getDocument(title, author);
        documentController.deleteDocument(title, author);
        authorController.deleteDocument(author, d);

        deleteIndividualDocumentMetadata(title,author);
    }

    /**
     * Funció per modificar el contingut d'un document.
     * @param oldTitle títol antic del document.
     * @param oldAuthor autor antic del document.
     * @param newTitle títol nou del document.
     * @param newAuthor autor nou del document.
     * @param newContent nou contingut del document.
     * @throws Exception Quan el document que es vol modificar no existeix.
     */
    public void modifyDocument(String oldTitle, String oldAuthor, String newTitle, String newAuthor, String newContent) throws Exception {
        if (!documentController.existsDocument(oldTitle, oldAuthor))
            throw new Exception("El document amb títol (" + oldTitle + ") i autor (" + oldAuthor + ") no existeix.");
        deleteDocument(oldTitle,oldAuthor);
        addDocument(newTitle,newAuthor,newContent);
    }


    /**
     * Retorna el títol, autor dels documents sense cambiar resultDocument, s'utilitza per llistar els documents de manera implícita.
     * @return es retorna una ArrayList de strings amb el títol i l'autor de cada document existent.
     */
    public ArrayList<String> getAllDocumentsNames() {
        ArrayList<String> out = new ArrayList<>();
        resultDocuments.setResult(documentController.getAllDocuments());
        ArrayList<Document> docs = resultDocuments.getDocs();
        for (Document doc : docs) {
            out.add(doc.getTitle() + ">" + doc.getAuthor());
        }
        return out;
    }

    /**
     * Funció per obtenir el contingut d'un Document.
     * @param title  títol del Document.
     * @param author autor del Document.
     * @return retorna una ArrayList de strings, on cada string es una frase del contingut del document.
     * @throws Exception En cas que el document no existeixi.
     */
    public String getContentWithTitleAuthor(String title, String author) throws Exception {
        if (!documentController.existsDocument(title, author))
            throw new Exception("El document amb títol (" + title + ") i autor (" + author + ") no existeix.");
        return documentController.getDocument(title, author).getContentString();
    }

    /**
     * Funció per obtenir els documents creats per un autor.
     * @param author nom de l'autor.
     * @return es retorna una ArrayList de strings amb el títol de cada document creat per l'autor.
     * @throws Exception En cas que l'autor no existeixi.
     */
    public ArrayList<String> getTitlesByAuthor(String author) throws Exception {
        if (!authorController.authorExists(author))
            throw new Exception("L'autor amb nom (" + author + ") no existeix.");
        ArrayList<Document> docs = authorController.getTitles(author);
        resultDocuments.setResult(docs);
        ArrayList<String> result = new ArrayList<>();
        for (Document doc : docs) {
            result.add(doc.getTitle());
        }
        return result;
    }

    /**
     * Funció per obtenir els documents que comencen per un prefix determinat per l'usuari.
     * @param prefix     prefix amb el qual es desitja filtrar els títols dels documents.
     * @param firstQuery booleà per indicar si volem realitzar una consulta sobre un altre resultat.
     * @return Els titols dels documents que comencen pel prefix indicat, en cas que no existeixi cap es retorna una llista buida.
     */
    public ArrayList<String> getTitlesByPrefix(String prefix, Boolean firstQuery) {
        if (firstQuery) resultDocuments.setResult(documentController.getAllDocuments());
        resultDocuments.getByPrefix(prefix);
        ArrayList<Document> docs = resultDocuments.getDocs();
        ArrayList<String> result = new ArrayList<>();
        for (Document doc : docs) {
            result.add(doc.getTitle() + ">" + doc.getAuthor());
        }
        return result;
    }

    /**
     * Funció per obtenir els k documents més similars a un altre.
     * @param title  títol del Document.
     * @param author autor del Document.
     * @param k      número de documents a buscar.
     * @return es retorna una ArrayList de strings amb el títol i autor de cada document més semblant.
     * @throws Exception En cas que el document no existeixi.
     */
    public ArrayList<String> getSimilarDocuments(String title, String author, Integer k) throws Exception {
        if (!documentController.existsDocument(title, author))
            throw new Exception("El document amb títol (" + title + ") i autor (" + author + ") no existeix.");
        ArrayList<Document> docs = documentController.getSimilarDocuments(title, author, k);
        resultDocuments.setResult(docs);
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < docs.size(); ++i) {
            Document doc = docs.get(i);
            result.add(i, doc.getTitle() + ">" + doc.getAuthor());
        }
        return result;
    }

    /**
     * Funció per retornar els títols de documents que compleixin una expressió booleana indicada per l'usuari.
     * @param exp        expressió booleana.
     * @param firstQuery indica si es vol realitzar la consulta sobre una altre.
     * @return es retorna una ArrayList de strings amb el títol i l'autor dels documents que compleixen l'expressió booleana.
     * @throws Exception Quan no es crea correctament una expressió booleana.
     */
    public ArrayList<String> getTitlesByBoolean(String exp, Boolean firstQuery) throws Exception {
        if (firstQuery) resultDocuments.setResult(documentController.getAllDocuments());
        if (!booleanController.existsExpression(exp)) booleanController.addExpression(exp);
        booleanController.useBooleanExpression(exp, resultDocuments);
        ArrayList<Document> docs = resultDocuments.getDocs();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < docs.size(); ++i) {
            Document doc = docs.get(i);
            result.add(i, doc.getTitle() + ">" + doc.getAuthor());
        }
        return result;
    }

    /**
     * Funció per retornar el resultat de documents anterior.
     * @return es retorna una ArrayList de strings amb el títol i l'autor dels documents del resultat anterior.
     */
    public ArrayList<String> getResultDocuments(){
        ArrayList<Document> docs = resultDocuments.getDocs();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < docs.size(); ++i) {
            Document doc = docs.get(i);
            result.add(i, doc.getTitle() + ">" + doc.getAuthor());
        }
        return result;
    }
    /**
     * Funció per saber si una expressió booleana ja existeix.
     * @param expression Expressió que es vol comparar.
     * @return Boolean amb valor true si ja existeix, false en cas contrari.
     */
    public Boolean existsBoolean(String expression) {
        return booleanController.existsExpression(expression);
    }

    /**
     * Funció per retornar totes les expressions booleanes.
     * @return ArrayList de String amb totes les expressions.
     */
    public ArrayList<String> getBooleanExpressions() {
        return new ArrayList<>(booleanController.getExpressions());
    }

    /**
     * Funció per eliminar una expressió booleana.
     * @param exp expressió booleana.
     * @throws Exception En cas que l'expressió a borrar no existeixi.
     */
    public void deleteBooleanExpression(String exp) throws Exception {
        if (!booleanController.existsExpression(exp))
            throw new Exception("L'expressió (" + exp + ") no existeix.");
        booleanController.eraseExpression(exp);
    }

    /**
     * Funció per modificar una expressió booleana.
     * @param exp1 expressió booleana a modificar.
     * @param exp2 expressió booleana modificada.
     * @throws Exception En cas que l'expressió a modificar no existeixi.
     */
    public void modifyBooleanExpression(String exp1, String exp2) throws Exception {
        if (!booleanController.existsExpression(exp1))
            throw new Exception("L'expressió (" + exp1 + ") no existeix.");
        if (booleanController.existsExpression(exp2))
            throw new Exception("L'expressió (" + exp2 + ") ja existeix.");
        booleanController.modifyExpression(exp1, exp2);
    }

    /**
     * Funció per obtenir els autors que comencen per un prefix indicat per l'usuari.
     * @param pref  prefix per comprovar els autors que comencen pel prefix.
     * @param order indica l'ordre amb el qual s'ha d'ordenar el resultat.
     * @return Una ArrayList de strings amb els autors que comencen pel prefix indicat.
     */
    public ArrayList<String> getAuthorsByPrefix(String pref, String order) {
        ArrayList<String> authors = authorController.getAuthors(pref);
        if (order.equals("1") && authors.size() > 0) authors.sort(Collections.reverseOrder());
        return authors;
    }

    /**
     * Descarrega el document en un arxiu al path que indica l'usuari.
     * @param title Títol del document.
     * @param author Autor del document.
     * @param path Path on l'usuari vol descarregar l'arxiu.
     * @throws IOException Quan hi ha un error en la descàrrega de l'arxiu.
     */
    public void downloadFile(String title, String author, String format, String path) throws IOException {
        Document d = documentController.getDocument(title,author);
        String content = d.getContentString();
        if (format.equals(".json")) {
            HashMap<String, Float> tf = d.getTfMap();
            persistenceController.downloadFile(title, author, content, path, format, tf);
        }
        else
            persistenceController.downloadFile(title, author, content, path, format);
    }

    /**
     * Llegeix un arxiu i l'afegeix al sistema.
     * @param paths ArrayList String de paths dels arxius que vol pujar l'usuari.
     * @throws Exception Quan no s'escriu el títol, l'autor i/o el contingut.
     */
    public void loadDocument(ArrayList<String> paths) throws Exception {
        for (String path : paths) {
            String[] data = persistenceController.loadFile(path);
            addDocument(data[0], data[1], data[2]);
            saveIndividualDocumentMetadata(data[0],data[1]);
        }
    }

    /**
     * Funció per guardar les dades d'un document document.
     * @param title Títol del document.
     * @param author Autor del document.
     */
    private void saveIndividualDocumentMetadata(String title, String author) {
        Document d = documentController.getDocument(title, author);
        String rawContent = d.getContentString();
        ArrayList<String> content = d.getContentList();
        HashMap<String, Float> tfMap = d.getTfMap();
        HashMap<String, Float> tfidfMap = d.getTfIdfMap();
        Float dist = d.getDist();
        DocumentData dd = new DocumentData(title, author, rawContent, content, tfMap, tfidfMap, dist);

        byte[] ddBytes = null;
        try {
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bs);
            os.writeObject(dd);
            os.close();
            ddBytes = bs.toByteArray();
        } catch (IOException e) {
            System.err.println("#Error al passar el DocumentData a bytes: " + e.getMessage());
            System.exit(1);
        }

        persistenceController.internalSaveFile(ddBytes, title, author);
    }

    /**
     * Funció per eliminar les dades d'un document.
     * @param title Títol del document.
     * @param author Autor del document.
     */
    private void deleteIndividualDocumentMetadata(String title, String author) {
        persistenceController.deleteFile(title,author);
    }

    /**
     * Funció per guardar l'arxiu dummy amb les dades que creiem necessàries.
     */
    public void manageDummyMetadata() {
        if (documentController.size() > 0 || booleanController.size() > 0) {
            HashMap<String, Integer> globalWordCount = documentController.getWordCount();
            ArrayList<String> booleanExpressions = booleanController.getExpressions();
            DummyData dummyData = new DummyData(globalWordCount, booleanExpressions);
            byte[] dummyDataBytes = null;
            try {
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bs);
                os.writeObject(dummyData);
                os.close();
                dummyDataBytes = bs.toByteArray();
            } catch (IOException e) {
                System.err.println("[#Error al passar el DummyData a bytes: " + e.getMessage());
                System.exit(1);
            }
            persistenceController.internalSaveDummy(dummyDataBytes);
        } else {
            persistenceController.internalDeleteDummy();
        }
    }

    /**
     * Funció per recarregar totes les dades en obrir el programa de nou.
     */
    public void reloadAll() {
        ArrayList<byte[]> documentDataBytes = persistenceController.internalLoadFiles();
        reloadAllDocuments(documentDataBytes);
        byte[] dummyDataBytes = persistenceController.internalLoadDummy();
        reloadDummyMetadata(dummyDataBytes);
        resultDocuments.setResult(documentController.getAllDocuments());
    }

    /**
     * Funció per recarregar tots els documents.
     * @param documentDataBytes ArrayList<byte[]> amb un document per cada posició de l'ArrayList.
     */
    private void reloadAllDocuments(ArrayList<byte[]> documentDataBytes) {
        for (byte[] bytesFile : documentDataBytes) {
            try {
                ByteArrayInputStream bs = new ByteArrayInputStream(bytesFile);
                ObjectInputStream is = new ObjectInputStream(bs);
                DocumentData dd = (DocumentData) is.readObject();

                reloadDocument(dd.title, dd.author, dd.rawContent, dd.content, dd.tfMap, dd.tfidfMap, dd.dist);

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Funció per recarregar cada document.
     * @param title Títol del document.
     * @param author Autor.
     * @param rawContent Contingut.
     * @param content Contingut separat per frases.
     * @param tfMap HashMap amb cada paraula i el seu tf.
     * @param tfidfMap HashMap amb cada paraula i el seu tfidf.
     * @param dist Distància.
     */
    private void reloadDocument(String title, String author, String rawContent, ArrayList<String> content, HashMap<String, Float> tfMap, HashMap<String, Float> tfidfMap, float dist) {
        documentController.reloadDocument(title, author, rawContent, content, tfMap, tfidfMap, dist);
        Document d = documentController.getDocument(title, author);
        if (authorController.authorExists(author)) authorController.addDocument(author, d);
        else authorController.addAuthor(author, d);
    }


    /**
     * Funció per recarregar el dummy.
     * @param dummyMetadataBytes byte[] amb el dummy serialitzat.
     */
    private void reloadDummyMetadata(byte[] dummyMetadataBytes) {
        try {
            ByteArrayInputStream bs = new ByteArrayInputStream(dummyMetadataBytes);
            ObjectInputStream is = new ObjectInputStream(bs);
            DummyData dummyData = (DummyData) is.readObject();
            documentController.reloadMetadata(dummyData.globalWordCount);
            booleanController.reloadExpressions(dummyData.booleanExpressions);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Funció per saber si és el primer cop que l'usuari obre el programa.
     * @return Boolean amb valor true si és el primer cop, false en cas contrari.
     */
    public Boolean firstTimeOpened() {
        return persistenceController.firstTimeOpened();
    }

    /**
     * Funció que elimina tots els arxius guardats.
     */
    public void deleteFiles() {
        persistenceController.deleteFiles();
    }
}
