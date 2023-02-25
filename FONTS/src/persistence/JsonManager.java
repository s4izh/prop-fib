package persistence;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class JsonManager extends FileManager {

    public JsonManager() {
    }

    /**
     * Llegeix un arxiu i el divideix en títol, autor i contingut.
     * @param path Path de l'arxiu que vol pujar l'usuari.
     * @return String[] amb el títol, autor i contingut en les posicions 0, 1 i 2, respectivament.
     * @throws Exception Quan no s'escriu el títol, l'autor i/o el contingut.
     */
    public String[] loadFile(String path) throws Exception {
        File file = new File(path);
        Scanner reader = new Scanner(file);
        StringBuilder jsonString = new StringBuilder();
        while (reader.hasNextLine()) {
            jsonString.append(reader.nextLine());
        }
        JSONObject json = new JSONObject(jsonString.toString());

        String title = json.getString("title");
        if (title.isBlank()) throw new Exception("el títol no pot ser buit");

        String author = json.getString("author");
        if (author.isBlank()) throw new Exception("l'autor no pot ser buit");

        String content = json.getString("content");
        if (content.isBlank()) throw new Exception("el contingut no pot ser buit");

        return new String[]{title, author, content};
    }

    /**
     * Descarrega el document en un arxiu al path que indica l'usuari.
     * @param title Títol del document.
     * @param author Autor del document.
     * @param content Contingut del document.
     * @param path Path on l'usuari vol descarregar l'arxiu.
     * @throws IOException En cas que hi hagi algun error amb la descàrrega.
     */
    public void downloadFile(String title, String author, String content, String path, HashMap<String, Float> tf) throws IOException {
        String destination = path + "/" + title + "--" + author + ".json";
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("content", content);

        JSONObject jsonMap = new JSONObject();
        for (Map.Entry<String, Float> entry : tf.entrySet()) {
            String key = entry.getKey();
            Float value = entry.getValue();
            jsonMap.put(key, value);
        }

        json.put("tf", jsonMap.toString());

        FileWriter f = new FileWriter(destination);
        f.write(json.toString());
        f.close();
    }
}
