package presentation;

import domain.controllers.DomainController;
import views.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PresentationController {

    /**
     * Instància del DomainController.
     */
    private final DomainController dc = DomainController.getInstance();

    /**
     * Instància del controlador de domini.
     */
    private static PresentationController singletonObject;

    /**
     * Booleà per indicar si volem realitzar una consulta sobre un altre resultat.
     */
    private Boolean firstQuery;

    /**
     * Vista de la finestra menú.
     */
    private ViewMenu viewMenu;

    /**
     * Vista de la finestra de les expressions booleanes.
     */
    private ViewBooleanExpressions viewBoolean;

    /**
     * Vista de la finestra per visualitzar documents.
     */
    private ViewVisualizeDocument viewVisualization;

    /**
     * Vista de la finestra per editar documents.
     */
    private ViewEditDocument viewEdit;

    /**
     * Booleà per indicar si és el primer cop que s'utilitza la vista d'edició.
     */
    private boolean firstTimeEdit = true;
    /**
     * Booleà per indicar si és el primer cop que s'utilitza la vista d'expressions booleanes.
     */
    private boolean firstTimeBoolean = true;
    /**
     * Booleà per indicar si és el primer cop que s'utilitza la vista per visualitzar documents.
     */
    private boolean firstTimeVisualize = true;


    /**
     * Inicialitza el DomainController i mostra en pantalla la finestra del menú principal.
     */
    public void iniPresentation() {
        dc.iniDomainController();
        firstQuery = true;
        if(dc.firstTimeOpened()){
            viewMenu();
        }
        else{
            new ViewInitialization();
        }
    }

    /**
     * Funció per recarregar o no les dades de la sessió anterior.
     * @param reuse Booleà que determina si l'usuari vol reutilitzar o no les dades de la sessió anterior.
     */
    public void iniPresentationReuse(Boolean reuse){
        if (reuse) dc.reloadAll();
        else dc.deleteFiles();
        viewMenu();
    }

    /**
     * Funció per retornar la instància del DomainController.
     * @return Una instància del DomainController.
     */
    public static PresentationController getInstance() {
        if (singletonObject == null) {
            singletonObject = new PresentationController();
        }
        return singletonObject;
    }

    /**
     * Mostra en pantalla la finestra d'edició d'un document.
     * @param title títol del document
     * @param author autor del document
     * @param create booleà per indicar si el document és nou o es vol modificar.
     * @throws Exception es llença una excepció si es modifica el títol i l'autor d'un document pel d'un ja existent.
     */
    public void viewEditDocument(String title, String author, boolean create) throws Exception {

        if(!firstTimeEdit){
            viewEdit.setValues(title, author, create);
            viewEdit.setVisible(true);
        }
        else{
            firstTimeEdit = false;
            viewEdit = new ViewEditDocument(title, author, create);
        }
    }

    /**
     * Mostra en pantalla la finestra de visualització d'un document.
     * @param title títol del document a visualitzar.
     * @param author autor del document a visualitzar.
     * @throws Exception Quan el document que es vol visualitzar no existeix.
     */
    public void viewVisualizeDocument(String title, String author) throws Exception {
        if(!firstTimeVisualize){
            viewVisualization.setValues(title, author);
            viewVisualization.setVisible(true);
        }
        else{
            firstTimeVisualize = false;
            viewVisualization = new ViewVisualizeDocument(title, author);
        }
    }

    /**
     * Mostra en pantalla la finestra per carregar un document.
     */
    public void viewLoadDocument() {
        new ViewLoadDocument();
    }


    /**
     * Crea la finestra del menú principal i la mostra en pantalla.
     */
    public void viewMenu() {
        viewMenu = new ViewMenu();
    }

    /**
     * Mostra en pantalla la finestra del menú principal.
     */
    public void visibleViewMenu(){
        viewMenu.setVisible(true);
    }

    /**
     * Mostra en pantalla una vista per seleccionar el directori d'on es desitja descarregar el document.
     * @param title títol del document.
     * @param author autor del document.
     * @param extension format del document.
     */
    public void viewDownloadFile(String title, String author, String extension) {
        new ViewDownloadFile(title, author, extension);
    }

    /**
     * Mostra en pantalla la finestra de gestió d'expressions booleanes.
     */
    public void viewBooleanExpressions() {
        if(!firstTimeBoolean){
            viewBoolean.setVisible(true);
            viewBoolean.listTableBooleanExpressions(getBooleanExpressions());
        }
        else{
            firstTimeBoolean = false;
            viewBoolean = new ViewBooleanExpressions();
        }
    }
    /**
     * Crea un document.
     * @param title   títol del document.
     * @param author  autor del document.
     * @param content contingut del document.
     */
    public void createDocument(String title, String author, String content) {
        try {
            dc.addDocument(title, author, content);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
        //viewMenu();
        visibleViewMenu();
        viewMenu.listTable(getAllDocuments());
    }

    /**
     * Comprova si ja existeix un document.
     * @param title títol del document.
     * @param author autor del document.
     * @return es retorna un booleà que indica cert si ja existia el document i fals en cas contrari.
     */
    public Boolean existsDocument(String title, String author){
        return dc.existsDocument(title, author);
    }

    /**
     * Retorna el contingut d'un document.
     * @param title títol del document.
     * @param author autor del document.
     * @return es retorna una string del contingut del document.
     * @throws Exception Si no existeix el document.
     */
    public String getContent(String title, String author) throws Exception {
        return dc.getContentWithTitleAuthor(title, author);
    }

    /**
     * Modifica el contingut d'un document
     * @param oldTitle títol antic del document.
     * @param oldAuthor autor antic del document.
     * @param newTitle títol nou del document.
     * @param newAuthor autor nou del document.
     * @param newContent nou contingut del document.
     * @throws Exception Quan no existeix el document que es vol modificar.
     */
    public void modifyDocument(String oldTitle, String oldAuthor, String newTitle, String newAuthor, String newContent) throws Exception {
        dc.modifyDocument(oldTitle, oldAuthor, newTitle, newAuthor, newContent);
        visibleViewMenu();
        viewMenu.listTable(getAllDocuments());
    }

    /**
     * Funció per modificar el contingut de la taula del menú principal en realitzar una consulta booleana des de la vista de gestió d'expressions booleanes.
     * @param docs ArrayList de strings amb els documents resultants.
     */
    public void setTableUsingBooleans(ArrayList<String> docs){
        viewMenu.listTable(docs);
    }

    /**
     * Elimina un document.
     * @param title  títol del document a eliminar.
     * @param author autor del document a eliminar.
     * @throws Exception Quan no existeixi el document identificat per títol i autor.
     */
    public void removeDocument(String title, String author) throws Exception {
        try {
            dc.deleteDocument(title, author);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Carrega un document.
     * @param paths Paths dels arxius o arxiu que es vol pujar.
     */
    public void loadDocument(ArrayList<String> paths) {
        try {
            dc.loadDocument(paths);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al carregar el document: " + e.getMessage());
        }
    }

    /**
     * Retorna els títols d'un autor específic.
     * @param author nom de l'autor.
     * @return es retorna una llista amb tots els títols de l'autor al gestor.
     * @throws Exception Si l'autor no existeix.
     */
    public ArrayList<String> getTitlesByAuthor(String author) throws Exception {
        firstQuery = false;
        return dc.getTitlesByAuthor(author);
    }

    /**
     * Retorna els k documents més semblants al document indicat.
     * @param title  títol del document seleccionat.
     * @param author autor del document seleccionat.
     * @param k      nombre de documents semblants.
     * @return es retorna una llista amb els k documents més semblants al document seleccionat.
     * @throws Exception Si no existeix el document identificat per títol i autor.
     */
    public ArrayList<String> getSimilarDocuments(String title, String author, Integer k) throws Exception {
        firstQuery = true;
        return dc.getSimilarDocuments(title, author, k);
    }

    /**
     * Descarrega un document.
     * @param title     títol del document seleccionat.
     * @param author    autor del document seleccionat.
     * @param extension format indicat per l'usuari per descarregar el document.
     * @param path      destí seleccionat per l'usuari en local per guardar el document.
     */
    public void downloadFile(String title, String author, String extension, String path) {
        try {
            dc.downloadFile(title, author, extension, path);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error descarregant el document: " + e.getMessage());
        }
    }

    /**
     * Filtra els títols per un prefix.
     * @param prefix prefix indicat per l'usuari.
     * @param newQuery   boolean per indicar si es vol realitzar la consulta sobre tots els documents o la llista d'una consulta.
     * @return ArrayList amb el títol i l'autor (en un sol String) dels documents on el seu títol comença pel prefix.
     */
    public ArrayList<String> getTitlesByPrefix(String prefix, Boolean newQuery) {
        firstQuery = newQuery;
        return dc.getTitlesByPrefix(prefix, newQuery);
    }

    /**
     * Filtra els autors per un prefix.
     * @param prefix prefix indicat per l'usuari.
     * @param order  ordre de la llista resultant, ascendent o descendent.
     * @return ArrayList amb el títol i l'autor (en un sol String) dels documents on el seu nom comença pel prefix.
     */
    public ArrayList<String> getAuthorsByPrefix(String prefix, String order) {
        return dc.getAuthorsByPrefix(prefix, order);
    }

    /**
     * Obté les expressions booleanes del sistema.
     * @return ArrayList amb totes les expressions booleanes del gestor.
     */
    public ArrayList<String> getBooleanExpressions() {
        return dc.getBooleanExpressions();
    }

    /**
     * Funció per saber si existeix una expressió booleana.
     * @param expression expressió booleana.
     * @return booleà que indica si existeix o no l'expressió booleana.
     */
    public Boolean existsBooleanExpression(String expression) {
        return dc.existsBoolean(expression);
    }

    /**
     * Modifica una expressió booleana.
     * @param newExpression expressió booleana modificada.
     * @param oldExpression expressió booleana a modificar.
     */
    public void modifyBooleanExpression(String newExpression, String oldExpression) {
        try {
            dc.modifyBooleanExpression(newExpression, oldExpression);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Elimina una expressió booleana.
     * @param expression expressió booleana a eliminar.
     * @throws Exception es llença una excepció si no existeix l'expressió booleana a eliminar.
     */
    public void deleteBooleanExpression(String expression) throws Exception {
        dc.deleteBooleanExpression(expression);
    }

    /**
     * Obté els títols dels documents que compleixen l'expressió booleana.
     * @param expression expressió booleana.
     * @param newQuery indica si la consulta es vol realitzar sobre tots els documents o una llista resultant d'una consulta prèvia.
     * @return es retorna una llista amb el títol i l'autor dels documents que compleixen l'expressió booleana.
     * @throws Exception Quan no es crea correctament una expressió booleana.
     */
    public ArrayList<String> getTitlesByBoolean(String expression, Boolean newQuery) throws Exception {
        if(firstQuery) firstQuery = false;
        else firstQuery = newQuery;
        return dc.getTitlesByBoolean(expression, newQuery);
    }

    /**
     * Obté tots els títols i autors dels documents.
     * @return es retorna una llista amb el títol i l'autor de tots els documents del gestor.
     */
    public ArrayList<String> getAllDocuments() {
        firstQuery = true;
        return dc.getAllDocumentsNames();
    }

    /**
     * Funció per retornar el resultat de documents anterior.
     * @return es retorna una ArrayList de string amb el títol i l'autor dels documents del resultat anterior.
     */
    public ArrayList<String> getResultDocuments(){
        return dc.getResultDocuments();
    }
    /**
     * Funció per guardar l'arxiu dummy amb les dades que creiem necessàries.
     */
    public void manageDummyMetadata() {
        dc.manageDummyMetadata();
    }

    /**
     * Funció per saber si es vol fer una query sobre query.
     * @return true si no es vol fer una query sobre query, false en cas que sí que es vulgui.
     */
    public Boolean getFirstQuery(){
        return firstQuery;
    }

    /**
     * Funció per reinicialitzar el valor del firstQuery a true.
     */
    public void setFirstQuery(){
        firstQuery = true;
    }
}