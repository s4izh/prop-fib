package persistence;

import java.util.Arrays;
import java.io.*;
import java.util.Scanner;

public class PlainTxtManager extends FileManager {

    /**
     * Creadora.
     */
    public PlainTxtManager() {
    }

    /**
     * Llegeix un arxiu i el divideix en títol, autor i contingut.
     * @param path Path de l'arxiu que vol pujar l'usuari.
     * @return String[] amb el títol, autor i contingut en les posicions 0, 1 i 2, respectivament.
     * @throws Exception Quan no s'escriu el títol, l'autor i/o el contingut.
     */
    public String[] loadFile(String path) throws Exception {
        String content = "";
        try {
            File file = new File(path);
            Scanner reader = new Scanner(file);
            String title = "";
            String author = "";
            StringBuilder tmp = new StringBuilder();
            int i = 0;
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                if (i == 0) {
                    title = data;
                    ++i;
                } else if (i == 1) {
                    author = data;
                    ++i;
                } else if (i == 2) {
                    tmp = new StringBuilder(data);
                    ++i;
                } else if (i > 2) {
                    if (!data.isBlank())
                        tmp.append("\n").append(data);
                    else tmp.append("\n");
                }
            }
            reader.close();

            if (title.isBlank()) throw new Exception("el títol no pot ser buit");
            if (author.isBlank()) throw new Exception("l'autor no pot ser buit");
            content = tmp.toString();
            if (content.isBlank()) throw new Exception("el contingut no pot ser buit");

            return new String[]{title, author, content};

        } catch (FileNotFoundException e) {
            throw new Exception("no existeix l'arxiu");
        }

    }

    /**
     * Descarrega el document en un arxiu al path que indica l'usuari.
     * @param title Títol del document.
     * @param author Autor del document.
     * @param content Contingut del document.
     * @param path Path on l'usuari vol descarregar l'arxiu.
     * @throws IOException En cas que hi hagi algun error amb la descàrrega.
     */
    @Override
    public void downloadFile(String title, String author, String content, String path) throws IOException{
        String destination = path + "/" + title + "--" + author + ".txt";

        new FileOutputStream(destination).close();
        FileOutputStream f = new FileOutputStream(destination, true);
        String header = title + "\r\n" + author + "\r\n";
        f.write(header.getBytes());
        f.write(content.getBytes());
        f.close();
    }
}
