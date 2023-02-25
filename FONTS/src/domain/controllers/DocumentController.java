package domain.controllers;

import domain.classes.Document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author Sergio Sanz Martínez
 */
public class DocumentController {
    /**
     * HashMap per guardar els documents amb clau <títol,autor> (només s'utilitzen dues posicions de l'Arraylist).
     */
    private HashMap<String, Document> docs;

    /**
     * HashMap per guardar les vegades que apareixen les paraules a tots els documents.
     */
    private HashMap<String, Integer> globalWordCount;

    /**
     * HashMap per guardar l'idf de totes les paraules de tots els documents.
     */
    private HashMap<String, Float> idfMap;

    /**
     * HashMap per guardar els documents similars de cada document.
     */
    private HashMap<String, ArrayList<Document>> similarDocs;

    /**
     * Booleà que serveix per guardar l'estat dels documents.
     */
    private Boolean modified;

    /**
     * Constructora de DocumentController.
     */
    public DocumentController() {
        docs = new HashMap<>();
        globalWordCount = new HashMap<>();
        similarDocs = new HashMap<>();
        modified = true;
    }

    /**
     * Constructora del DocumentController quan es fa el reload.
     * @param globalWordCount HashMap per guardar les vegades que apareixen les paraules a tots els documents.
     */
    public DocumentController(HashMap<String,Integer> globalWordCount) {
        this.globalWordCount = globalWordCount;
        modified = false;
    }

    /**
     * Funció per comprobar si existeix un Document.
     * @param title  títol del Document.
     * @param author autor del Document.
     * @return retorna true si existeix el Document amb títol i autor, fals en cas contrari.
     */
    public boolean existsDocument(String title, String author) {
        String k = title + "//" + author;
        return docs.containsKey(k);
    }

    /**
     * Funció per obtenir un Document donats un títol i un autor.
     * @param title  títol del Document.
     * @param author autor del Document.
     * @return Document que compleix els paràmetres.
     */
    public Document getDocument(String title, String author) {
        String k = title + "//" + author;
        return docs.get(k);
    }

    /**
     * Funció per obtenir un Document donada una String títol+autor.
     * @param k String títol+"//"+autor.
     * @return Document que compleix el paràmetre.
     */
    private Document getDocument(String k) {
        return docs.get(k);
    }

    /**
     * Funció per obtenir tots els Documents.
     * @return ArrayList amb tots els Documents.
     */
    public ArrayList<Document> getAllDocuments() {
        ArrayList<Document> documents = new ArrayList<>();
        for (Entry<String, Document> doc : docs.entrySet()) {
            documents.add(doc.getValue());
        }
        return orderByTitle(documents);
    }

    /**
     * Funció per afegir un Document donats un títol, autor i contingut.
     * @param title     títol del Document.
     * @param author    autor del Document.
     * @param content   contingut del Document.
     */
    public void addDocument(String title, String author, String content) {
        String k = title + "//" + author;
        Document doc = new Document(title, author, content);
        recalculateWordCount(doc, true);
        docs.put(k, doc);
        modified = true;
        similarDocs.put(k, new ArrayList<>());
    }

    /**
     * Funció per esborrar un Document donats un títol i un autor.
     * @param title  títol del Document.
     * @param author autor del Document.
     */
    public void deleteDocument(String title, String author) {
        String k = title + "//" + author;
        recalculateWordCount(docs.get(k), false);
        docs.remove(k);
        modified = true;
    }

    /**
     * Funció per calcular l'idf de totes les paraules presents a tots els documents.
     */
    public void calculateIdf() {
        HashMap<String, Float> tempMap = new HashMap<>();
        float n = (float) docs.size();
        for (Entry<String, Integer> count : globalWordCount.entrySet()) {
            float temp = n / Float.valueOf(count.getValue());
            float idf = (float) Math.log10(temp);
            tempMap.put(count.getKey(), idf);
        }
        idfMap = tempMap;
    }

    /**
     * Funció per recalcular el nombre de vegades que apareixen les paraules a tots els documents quan afegim/esborrem un Document.
     * @param doc Document que afegim/esborrem.
     * @param add true si estem afegint un Document, fals si no.
     */
    private void recalculateWordCount(Document doc, Boolean add) {
        HashMap<String, Float> tempMap = doc.getTfMap();
        if (add) {
            for (String word : tempMap.keySet()) {
                if (!globalWordCount.containsKey(word))
                    globalWordCount.put(word, 1);
                else globalWordCount.replace(word, (globalWordCount.get(word) + 1));
            }
        } else {
            for (String word : tempMap.keySet()) {
                if (globalWordCount.containsKey(word) && globalWordCount.get(word) > 1)
                    globalWordCount.replace(word, globalWordCount.get(word) - 1);
                else globalWordCount.remove(word);
            }
        }
    }

    /**
     * Funció que retorna k documents similars a un altre document.
     * @param title  títol del Document del qual es volen aconseguir documents similars.
     * @param author autor del Document del qual es volen aconseguir documents similars.
     * @param k      número de documents que es volen obtenir.
     * @return ArrayList de k Documents ordenats de major a menor similitud,
     * si k es major nombre de documents maxim, es retornaran el màxim de documents.
     */
    public ArrayList<Document> getSimilarDocuments(String title, String author, Integer k) {
        ArrayList<Document> result = new ArrayList<>();
        String d1 = title + "//" + author;
        ArrayList<Document> similarDocsList = similarDocs.get(d1);
        if (modified || similarDocsList.isEmpty()) {
            Document doc1 = getDocument(d1);
            HashMap<String, Float> doc1tfidf;
            // només fa falta recalcular idf i tfidf si hi ha hagut alguna modificació als documents
            if (modified) {
                calculateIdf();
                doc1tfidf = doc1.calculateTfIdf(idfMap);
            } else doc1tfidf = doc1.getTfIdfMap();
            // hashmap temporal amb document - cosine similarity
            HashMap<String, Float> doc1simDocMap = new HashMap<>();
            for (Entry<String, Document> entry : docs.entrySet()) {
                Document doc2 = entry.getValue();
                if (!(doc2.getTitle().equals(title) && doc2.getAuthor().equals(author))) {
                    HashMap<String, Float> doc2tfidf;
                    // només fa falta recalcular tfidf si hi ha hagut alguna modificació als documents
                    if (modified) {
                        doc2tfidf = doc2.calculateTfIdf(idfMap);
                    } else doc2tfidf = doc2.getTfIdfMap();
                    float sum = 0;
                    for (Entry<String, Float> entry2 : doc1tfidf.entrySet()) {
                        String word = entry2.getKey();
                        if (doc2tfidf.containsKey(word))
                            sum += doc1tfidf.get(word) * doc2tfidf.get(word);
                    }
                    float sim = sum / (doc1.getDist() * doc2.getDist());
                    String d2 = doc2.getTitle() + "//" + doc2.getAuthor();
                    doc1simDocMap.put(d2, sim);
                }
            }
            // ArrayList amb Document - Documents similars ordenats
            similarDocs.put(d1, sortByValue(doc1simDocMap));
            similarDocsList = similarDocs.get(d1);
            modified = false;
        }
        for (int i = 0; i < Math.min(k, docs.size() - 1); ++i) {
            result.add(similarDocsList.get(i));
        }
        return result;
    }

    /**
     * Funció que ordena els documents similars segons el seu cosine similarity.
     * @param sim Hashmap amb la clau del document i el seu cosine similarity.
     * @return ArrayList de Documents ordenats segons la seva similitud, de major a menor.
     */
    private ArrayList<Document> sortByValue(HashMap<String, Float> sim) {
        ArrayList<Entry<String, Float>> list = new ArrayList<>(sim.entrySet());
        list.sort((d1, d2) -> (d2.getValue()).compareTo(d1.getValue()));
        ArrayList<Document> similarDocsOrdered = new ArrayList<>();
        for (Entry<String, Float> entry : list) {
            similarDocsOrdered.add(getDocument(entry.getKey()));
        }
        return similarDocsOrdered;
    }

    /**
     * Ordena una ArrayList de documents.
     * @param docs ArrayList de documents.
     * @return Arraylist ordenada pel títol.
     */
    private ArrayList<Document> orderByTitle(ArrayList<Document> docs) {
        docs.sort(new Comparator<Document>() {
            @Override
            public int compare(Document d1, Document d2) {
                if (d1.getTitle().compareTo(d2.getTitle()) != 0)
                    return d1.getTitle().compareTo(d2.getTitle());
                else return d1.getAuthor().compareTo(d2.getAuthor());
            }
        });
        return docs;
    }

    // -------- funcions per utilitzar als test --------

    /**
     * Funció per retornar el nombre de documents.
     * @return nombre de documents al sistema.
     */
    public int size() {
        return docs.size();
    }

    /**
     * Funció per retornar el globalWordCount.
     * @return Hashmap amb cada paraula i les vegades que apareix.
     */
    public HashMap<String, Integer> getWordCount() {
        return globalWordCount;
    }

    /**
     * Funció per retornar l'idfMap.
     * @return Hashmap amb cada paraula i el seu idf.
     */
    public HashMap<String, Float> getIdfMap() {
        return idfMap;
    }

    /**
     * Funció per recarregar els documents.
     * @param title Títol.
     * @param author Autor.
     * @param rawContent Contingut.
     * @param content Contingut separat per frases.
     * @param tfMap HashMap amb el tf.
     * @param tfidfMap HashMap amb el tfidf.
     * @param dist Distància del vector tfidf calculat del Document.
     */
    public void reloadDocument(String title, String author, String rawContent, ArrayList<String> content, HashMap<String, Float> tfMap, HashMap<String, Float> tfidfMap, float dist) {
        String k = title + "//" + author;
        Document doc = new Document(title, author, rawContent, content, tfMap, tfidfMap, dist);
        docs.put(k, doc);
        similarDocs.put(k, new ArrayList<>());
    }

    /**
     * Funció per recarregar el valor del globalWordCount en fer reload de l'aplicació.
     * @param globalWordCount HashMap per guardar les vegades que apareixen les paraules a tots els documents.
     */
    public void reloadMetadata(HashMap<String, Integer> globalWordCount)
    {
        this.globalWordCount = globalWordCount;
    }
}
