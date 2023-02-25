package domain.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DocumentData implements Serializable {
    /**
     * Títol del document.
     */
    public final String title;

    /**
     * Autor del document.
     */
    public final String author;

    /**
     * Contingut del document.
     */
    public final String rawContent;

    /**
     * ArrayList del contingut del document dividit per frases.
     */
    public final ArrayList<String> content;

    /**
     * HashMap amb cada paraula del document i el seu tf.
     */
    public final HashMap<String, Float> tfMap;

    /**
     * HashMap amb cada paraula del document i el seu tfidf.
     */
    public final HashMap<String, Float> tfidfMap;

    /**
     * Distància del vector tfidf calculat del Document
     */
    public final float dist;

    /**
     * Creadora.
     * @param title Títol.
     * @param author Autor.
     * @param rawContent Contingut.
     * @param content Contingut separat per frases.
     * @param tfMap HashMap amb el tf.
     * @param tfidfMap HashMap amb el tfidf.
     * @param dist Distància del vector tfidf calculat del Document.
     */
    public DocumentData(String title, String author, String rawContent, ArrayList<String> content, HashMap<String, Float> tfMap, HashMap<String, Float> tfidfMap, Float dist) {
        this.title = title;
        this.author = author;
        this.rawContent = rawContent;
        this.content = content;
        this.tfMap = tfMap;
        this.tfidfMap = tfidfMap;
        this.dist = dist;
    }
}
