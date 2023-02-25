package persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MetadataManager {

    /**
     * Path on es guarden les dades del sistema.
     */
    private final String path = System.getProperty("user.dir") + "/data/";

    /**
     * Creadora.
     */
    public MetadataManager() {
    }

    /**
     * Guarda internament un document.
     * @param dd byte[] amb els bytes del DocumentData.
     * @param title Títol del document.
     * @param author Autor del document.
     */
    public void internalSaveFile(byte[] dd, String title, String author) {
        String pathFile = path + "documents/" + title + "--" + author;
        try {
            FileOutputStream outFile = new FileOutputStream(pathFile);
            outFile.write(dd);
            outFile.flush();
            outFile.close();
        } catch (IOException e) {
            System.err.println("Error al guardar internament un document: " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Carrega tots els documents guardats al sistema.
     * @return ArrayList byte[] amb un document per cada posició de l'ArrayList.
     */
    public ArrayList<byte[]> internalLoadFiles() {
        ArrayList<byte[]> bytesArrayList = new ArrayList<>();
        String pathFiles = path + "documents/";
        File directory = new File(pathFiles);
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            try {
                FileInputStream fileInputStream = new FileInputStream(pathFiles + file.getName());
                bytesArrayList.add(fileInputStream.readAllBytes());
                fileInputStream.close();
            } catch (IOException e) {
                System.err.println("Error al carregar els arxius interns: " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
            }
        }
        return bytesArrayList;
    }

    /**
     * Guarda internament el dummy amb les dades que desitgem.
     * @param dummyDataBytes byte[] amb el dummy serialitzat.
     */
    public void internalSaveDummy(byte[] dummyDataBytes) {
        String pathFile = path + "dummy";
        try {
            FileOutputStream outFile = new FileOutputStream(pathFile);
            outFile.write(dummyDataBytes);
            outFile.flush();
            outFile.close();
        } catch (IOException e) {
            System.err.println("Error al guardar internament l'arxiu dummy: " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * Carrega el dummy guardat al sistema.
     * @return byte[] amb els bytes del dummy.
     */
    public byte[] internalLoadDummy() {
        String pathFile = path + "dummy";
        byte[] bytes = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(pathFile);
            bytes = fileInputStream.readAllBytes();
            fileInputStream.close();
        }
        catch (IOException e) { System.err.println("Error al deserialitzar: " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace())); }
        return bytes;
    }

    /**
     * Funció per saber si és el primer cop que l'usuari obre el programa.
     * @return Boolean amb valor true si és el primer cop, false en cas contrari.
     */
    public Boolean firstTimeOpened() {
        File directory = new File(path + "documents/");
        File dummy = new File(path + "dummy");
        File[] files = directory.listFiles();
        assert files != null;
        return (files.length == 0) && (!dummy.exists());
    }

    /**
     * Funció que elimina tots els arxius guardats.
     */
    public void deleteFiles() {
        File dummy = new File(path + "dummy");
        dummy.delete();
        File documentFiles = new File(path + "documents/");
        File[] files = documentFiles.listFiles();
        assert files != null;
        for (File file : files) {
            file.delete();
        }
    }

    /**
     * Funció per eliminar les dades d'un document.
     * @param title Títol del document.
     * @param author Autor del document.
     */
    public void deleteFile(String title, String author) {
        File documentFile = new File(path + "documents/" + title + "--" + author);
        documentFile.delete();
    }

    /**
     * Borra l'arxiu dummy.
     */
    public void deleteDummy() {
        File dummy = new File(path + "dummy");
        dummy.delete();
    }
}