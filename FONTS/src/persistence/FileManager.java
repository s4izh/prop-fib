package persistence;

import java.io.IOException;
import java.util.HashMap;

abstract public class FileManager {

    /**
     * Creadora.
     */
    public FileManager() {
    }

    /**
     * Llegeix un arxiu i el divideix en títol, autor i contingut.
     * @param path Path de l'arxiu que vol pujar l'usuari.
     * @return String[] amb el títol, autor i contingut en les posicions 0, 1 i 2, respectivament.
     * @throws Exception Quan no s'escriu el títol, l'autor i/o el contingut.
     */
    public String[] loadFile(String path) throws Exception {
        return null;
    }

    /**
     * Descarrega el document en un arxiu al path que indica l'usuari.
     * @param title Títol del document.
     * @param author Autor del document.
     * @param content Contingut del document.
     * @param path Path on l'usuari vol descarregar l'arxiu.
     * @throws IOException Quan hi ha un error en la descàrrega de l'arxiu.
     */
    public void downloadFile(String title, String author, String content, String path) throws IOException {
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
    public void downloadFile(String title, String author, String content, String path, HashMap<String, Float> tf) throws IOException {
    }
}
