package domain.classes;

import domain.node.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author Sergio Sanz Martínez
 */
public class Document {
    /**
     * Títol del Document.
     */
    private final String title;

    /**
     * Autor del Document.
     */
    private final String author;

    /**
     * String amb tot el contingut del Document mantenint el format original.
     */
    private String rawContent;

    /**
     * ArrayList amb les frases del Document.
     */
    private ArrayList<Phrase> content;

    /**
     * HashMap per la freqüència inversa de les paraules del Document.
     */
    private HashMap<String, Float> tfMap;

    /**
     * HashMap del vector tdidf del Document.
     */
    private HashMap<String, Float> tfidfMap;

    /**
     * Distància del vector tfidf calculat del Document.
     */
    private float dist;

    /**
     * ArrayList amb les paraules que no es tenen en compte a l'hora de contar les paraules del Document.
     */
    private final ArrayList<String> stopWords = new ArrayList<>(Arrays.asList("de", "la", "que", "el", "en", "y", "a", "los", "del", "se", "las", "por", "un", "para", "con", "no", "una", "su", "al", "lo", "como", "más", "pero", "sus", "le", "ya", "o", "este", "sí", "porque", "esta", "entre", "cuando", "muy", "sin", "sobre", "también", "me", "hasta", "hay", "donde", "quien", "desde", "todo", "nos", "durante", "todos", "uno", "les", "ni", "contra", "otros", "ese", "eso", "ante", "ellos", "e", "esto", "mí", "antes", "algunos", "qué", "unos", "yo", "otro", "otras", "otra", "él", "tanto", "esa", "estos", "mucho", "quienes", "nada", "muchos", "cual", "poco", "ella", "estar", "estas", "algunas", "algo", "nosotros", "mi", "mis", "tú", "te", "ti", "tu", "tus", "ellas", "nosotras", "vosotros", "vosotras", "os", "mío", "mía", "míos", "mías", "tuyo", "tuya", "tuyos", "tuyas", "suyo", "suya", "suyos", "suyas", "nuestro", "nuestra", "nuestros", "nuestras", "vuestro", "vuestra", "vuestros", "vuestras", "esos", "esas", "estoy", "estás", "está", "estamos", "estáis", "están", "esté", "estés", "estemos", "estéis", "estén", "estaré", "estarás", "estará", "estaremos", "estaréis", "estarán", "estaría", "estarías", "estaríamos", "estaríais", "estarían", "estaba", "estabas", "estábamos", "estabais", "estaban", "estuve", "estuviste", "estuvo", "estuvimos", "estuvisteis", "estuvieron", "estuviera", "estuvieras", "estuviéramos", "estuvierais", "estuvieran", "estuviese", "estuvieses", "estuviésemos", "estuvieseis", "estuviesen", "estando", "estado", "estada", "estados", "estadas", "estad", "he", "has", "ha", "hemos", "habéis", "han", "haya", "hayas", "hayamos", "hayáis", "hayan", "habré", "habrás", "habrá", "habremos", "habréis", "habrán", "habría", "habrías", "habríamos", "habríais", "habrían", "había", "habías", "habíamos", "habíais", "habían", "hube", "hubiste", "hubo", "hubimos", "hubisteis", "hubieron", "hubiera", "hubieras", "hubiéramos", "hubierais", "hubieran", "hubiese", "hubieses", "hubiésemos", "hubieseis", "hubiesen", "habiendo", "habido", "habida", "habidos", "habidas", "soy", "eres", "es", "somos", "sois", "son", "sea", "seas", "seamos", "seáis", "sean", "seré", "serás", "será", "seremos", "seréis", "serán", "sería", "serías", "seríamos", "seríais", "serían", "era", "eras", "éramos", "erais", "eran", "fui", "fuiste", "fue", "fuimos", "fuisteis", "fueron", "fuera", "fueras", "fuéramos", "fuerais", "fueran", "fuese", "fueses", "fuésemos", "fueseis", "fuesen", "sintiendo", "sentido", "sentida", "sentidos", "sentidas", "siente", "sentid", "tengo", "tienes", "tiene", "tenemos", "tenéis", "tienen", "tenga", "tengas", "tengamos", "tengáis", "tengan", "tendré", "tendrás", "tendrá", "tendremos", "tendréis", "tendrán", "tendría", "tendrías", "tendríamos", "tendríais", "tendrían", "tenía", "tenías", "teníamos", "teníais", "tenían", "tuve", "tuviste", "tuvo", "tuvimos", "tuvisteis", "tuvieron", "tuviera", "tuvieras", "tuviéramos", "tuvierais", "tuvieran", "tuviese", "tuvieses", "tuviésemos", "tuvieseis", "tuviesen", "teniendo", "tenido", "tenida", "tenidos", "tenidas", "tened"));

    /**
     * Constructora de document.
     *
     * @param title   títol del Document.
     * @param author  autor del Document.
     * @param content contingut del Document.
     */
    public Document(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.rawContent = content;
        parseContent(content);
        tfidfMap = new HashMap<>();
    }

    /**
     * Constructora de document.
     *
     * @param title    títol del Document.
     * @param author   autor del Document.
     * @param content  contingut del Document.
     * @param tfMap    vector tf del Document.
     * @param tfidfMap vector tfidf del Document.
     * @param dist     distància del vector tfidf.
     */
    public Document(String title, String author, String rawContent, ArrayList<String> content, HashMap<String, Float> tfMap, HashMap<String, Float> tfidfMap, Float dist) {
        this.title = title;
        this.author = author;
        this.rawContent = rawContent;
        this.tfMap = tfMap;
        this.tfidfMap = tfidfMap;
        this.dist = dist;
        this.content = new ArrayList<>();
        for (String phrase : content) {
            this.content.add(new Phrase(phrase));
        }
    }

    /**
     * Getter del títol.
     *
     * @return títol del Document.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter del autor.
     *
     * @return autor del Document.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Getter del contingut del Document.
     *
     * @return String amb tot el contingut del Document
     */
    public String getContentString() {
        return rawContent;
    }

    /**
     * Getter del contingut del Document.
     *
     * @return ArrayList de String amb les frases del Document.
     */
    public ArrayList<String> getContentList() {
        ArrayList<String> c = new ArrayList<>();
        for (Phrase s : content) c.add(s.getPhrase());
        return c;
    }

    /**
     * Getter de la freqüència inversa de cada paraula.
     *
     * @return HashMap amb cada paraula i la seva freqüència inversa al Document.
     */
    public HashMap<String, Float> getTfMap() {
        return tfMap;
    }

    /**
     * Funció per obtenir el vector tfidf del Document.
     *
     * @return HashMap amb cada paraula i el seu tfidf.
     */
    public HashMap<String, Float> getTfIdfMap() {
        return tfidfMap;
    }

    /**
     * Getter de la distància del vector tfidf.
     *
     * @return Double amb la distància del vector tfidf.
     */
    public float getDist() {
        return dist;
    }

    /**
     * Funció que a partir de un String amb tot el contingut, calcula
     * el tfMap del document i crea un ArrayList amb les frases del document
     *
     * @param content Contingut del document.
     */
    private void parseContent(String content) {
        HashMap<String, Integer> wordCount = new HashMap<>();

        String words[] = content.split("\\s+");
        String lastWord = words[words.length - 1];

        boolean newPhrase = true;
        StringBuilder phrase = new StringBuilder();

        this.content = new ArrayList<>();

        for (String word : words) {
            if (!word.isBlank()) {
                if (newPhrase) {
                    phrase = new StringBuilder();
                    newPhrase = false;
                }
                char lastWordChar = word.charAt(word.length() - 1);
                phrase.append(word);
                if (lastWordChar == '.' || lastWordChar == '!' || lastWordChar == '?' || word.equals(lastWord)) {
                    if (word.equals(lastWord) && !(lastWordChar == '.' || lastWordChar == '!' || lastWordChar == '?'))
                        phrase.append(".");
                    this.content.add(new Phrase(phrase.toString()));
                    newPhrase = true;
                } else phrase.append(" ");
                String parsedWord = word.replaceAll("[^a-zA-Z0-9]", "");
                if (!stopWords.contains(parsedWord) && !parsedWord.equals("")) {
                    if (!wordCount.containsKey(parsedWord)) wordCount.put(parsedWord, 1);
                    else wordCount.replace(parsedWord, (wordCount.get(parsedWord) + 1));
                }
            }
        }
        calculateTf(wordCount);
    }

    /**
     * Funció que calcula la freqüència de cada paraula al Document i la
     * guarda a tfMap.
     * 
     * @param wordCount HashMap amb cada paraula del document i les vegades que apareix
     */
    private void calculateTf(HashMap<String, Integer> wordCount) {
        tfMap = new HashMap<>();
        float sum = 0;
        // suma de totes les paraules
        for (float val : wordCount.values()) {
            sum += val;
        }
        // calcular el tf
        for (Entry<String, Integer> entry : wordCount.entrySet()) {
            float tf = entry.getValue() / sum;
            tfMap.put(entry.getKey(), tf);
        }
    }

    /**
     * Funció per calcular el vector tfidf del Document.
     *
     * @param idfMap HashMap amb paraula - frequència inversa de cada paraula
     *               a tots els documents.
     * @return HashMap amb cada paraula i el seu tfidf.
     */
    public HashMap<String, Float> calculateTfIdf(HashMap<String, Float> idfMap) {
        HashMap<String, Float> tfidfMap = new HashMap<>();
        float tempDist = 0;
        float tfidf;
        for (Entry<String, Float> entry : tfMap.entrySet()) {
            String word = entry.getKey();
            tfidf = entry.getValue() * idfMap.get(word);
            tfidfMap.put(word, tfidf);
            tempDist += Math.pow(tfidf, 2);
        }
        dist = (float) Math.sqrt(tempDist);
        this.tfidfMap = tfidfMap;
        return tfidfMap;
    }

    /**
     * Funció per veure si hi ha alguna Phrase al Document que satisfaci una
     * expressió booleana.
     *
     * @param root Node arrel de l'arbre que conté l'expressió booleana.
     * @return True si el Document té alguna Phrase que compleix l'expressió
     * booleana, false en cas contrari.
     */
    public Boolean evaluateBooleanExpression(Node root) {
        for (Phrase i : content) {
            if (i.evaluate(root)) return true;
        }
        return false;
    }
}
