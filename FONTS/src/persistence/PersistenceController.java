package persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PersistenceController {

    /**
     * HashMap amb els tipus de formats que acceptem.
     */
    private HashMap<String, FileManager> fileManagers;

    /**
     * Relació amb el Metadatamanager.
     */
    private MetadataManager metadataManager;

    /**
     * Instància del PersistenceController.
     */
    private static PersistenceController singletonObject;

    /**
     * Creadora.
     */
    public PersistenceController() {
    }

    /**
     * Funció per retornar una instància del PersistenceController.
     * @return Una instància del PersistenceController.
     */
    public static PersistenceController getInstance() {
        if (singletonObject == null) {
            singletonObject = new PersistenceController();
        }
        return singletonObject;
    }

    /**
     * Funció per inicialitzar els atributs.
     */
    public void iniPersistenceController() {
        fileManagers = new HashMap<>();
        fileManagers.put(".txt", new PlainTxtManager());
        fileManagers.put(".xml", new XmlManager());
        fileManagers.put(".json", new JsonManager());
        metadataManager = new MetadataManager();
    }

    /**
     * Guarda internament un document.
     * @param documentData byte[] amb els bytes del DocumentData.
     * @param title Títol del document.
     * @param author Autor del document.
     */
    public void internalSaveFile(byte[] documentData, String title, String author) {
        metadataManager.internalSaveFile(documentData, title, author);
    }

    /**
     * Guarda internament el dummy amb les dades que desitgem.
     * @param dummyDataBytes byte[] amb el dummy serialitzat.
     */
    public void internalSaveDummy(byte[] dummyDataBytes) {
        metadataManager.internalSaveDummy(dummyDataBytes);
    }

    /**
     * Carrega tots els documents guardats al sistema.
     * @return ArrayList byte[] amb un document per cada posició de l'ArrayList.
     */
    public ArrayList<byte[]> internalLoadFiles() {
        return metadataManager.internalLoadFiles();
    }

    /**
     * Carrega el dummy guardat al sistema.
     * @return byte[] amb els bytes del dummy.
     */
    public byte[] internalLoadDummy() {
        return metadataManager.internalLoadDummy();
    }

    /**
     * Llegeix un arxiu i el divideix en títol, autor i contingut.
     * @param path Path de l'arxiu que vol pujar l'usuari.
     * @return String[] amb el títol, autor i contingut en les posicions 0, 1 i 2, respectivament.
     * @throws Exception Quan no s'escriu el títol, l'autor i/o el contingut.
     */
    public String[] loadFile(String path) throws Exception {
        int pos = -1;
        boolean found = false;
        for (int i = path.length() - 1; i >= 0 && !found; --i) {
            if (path.charAt(i) == '.') {
                pos = i;
                found = true;
            }
        }
        if (!found) throw new Exception("L'arxiu no té cap extensió");
        StringBuilder format = new StringBuilder();
        for (int i = pos; i < path.length(); ++i) {
            format.append(path.charAt(i));
        }
        if (!format.toString().equals(".txt") && !format.toString().equals(".xml") && !format.toString().equals(".json"))
            throw new Exception("L'extensió de l'arxiu és incorrecte.");

        return fileManagers.get(format.toString()).loadFile(path);
    }

    /**
     * Descarrega el document en un arxiu al path que indica l'usuari.
     * @param title Títol del document.
     * @param author Autor del document.
     * @param content Contingut del document.
     * @param path Path on l'usuari vol descarregar l'arxiu.
     * @throws IOException Quan hi ha un error en la descàrrega de l'arxiu.
     */
    public void downloadFile(String title, String author, String content, String path, String format) throws IOException {
        fileManagers.get(format).downloadFile(title, author, content, path);
    }

    /**
     * Descarrega el document en un arxiu al path que indica l'usuari.
     * @param title Títol del document.
     * @param author Autor del document.
     * @param content Contingut del document.
     * @param path Path on l'usuari vol descarregar l'arxiu.
     * @param tf HashMap amb cada paraula del document i la seva freqüència inversa
     * @throws IOException Quan hi ha un error en la descàrrega de l'arxiu.
     */
    public void downloadFile(String title, String author, String content, String path, String format, HashMap<String, Float> tf) throws IOException {
        fileManagers.get(format).downloadFile(title, author, content, path, tf);
    }

    /**
     * Funció per saber si és el primer cop que l'usuari obre el programa.
     * @return Boolean amb valor true si és el primer cop, false en cas contrari.
     */
    public Boolean firstTimeOpened() {
        return metadataManager.firstTimeOpened();
    }

    /**
     * Funció que elimina tots els arxius guardats.
     */
    public void deleteFiles() {
        metadataManager.deleteFiles();
    }

    /**
     * Funció per eliminar les dades d'un document.
     * @param title Títol del document.
     * @param author Autor del document.
     */
    public void deleteFile(String title, String author) {
        metadataManager.deleteFile(title,author);
    }

    /**
     * Borra l'arxiu dummy.
     */
    public void internalDeleteDummy() {
        metadataManager.deleteDummy();
    }
}

