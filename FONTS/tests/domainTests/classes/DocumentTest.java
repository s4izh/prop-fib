package domainTests.classes;

import domain.classes.Document;
import domain.node.AndNode;
import domain.node.Node;
import domain.node.OrNode;
import domain.node.StringNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author Sergio Sanz Martínez
 */
public class DocumentTest {

    String content = "zocaso zocaso zocaso zocaso gato gato gato mesa mesa silla.";
    String contentBool = "el pastel de zocaso. la casa de jose. la casa de juan.";

    /**
     * Test que comproba que s'obtingui el títol correctament
     */
    @Test
    public void getTitle() {
        Document doc = new Document("test", "Pol", "este es el nuevo contenido.");
        assertEquals("test", doc.getTitle());
    }

    /**
     * Test que comproba que s'obtingui l'autor correctament
     */
    @Test
    public void getAuthor() {
        //ArrayList<String> content = createContent();
        //HashMap<String, Integer> docWordCount = getWordCount(content);
        Document doc = new Document("test", "Pol", "este documento se usa para devolver el autor.");
        assertEquals("Pol", doc.getAuthor());
    }

    /**
     * Test que comproba que la funció getContent() funciona correctament
     */
    @Test
    public void getContentString() {
        Document doc = new Document("el sol", "dante", content);
        String test = doc.getContentString();
        assertEquals(content, test);
    }

    /**
     * Test que comproba que la funció getContent2() funciona correctament
     */
    @Test
    public void getContentList() {
        Document doc = new Document("el sol", "dante", contentBool);
        ArrayList<String> result = doc.getContentList();
        StringBuilder sb = new StringBuilder();
        boolean firstPhrase = true;
        for (String t : result) {
            if (!firstPhrase) {
               sb.append(" ");
            }
            else firstPhrase = false;
            sb.append(t);
        }
        assertEquals(contentBool, sb.toString());
    }

    /**
     * Test que comproba que funcioni calculateTf() i getTfMap()
     */
    @Test
    public void calculateTf() {
        // la constructora de document ja utiliza calculateTf()
        Document doc = new Document("el sol", "dante", content);
        HashMap<String, Float> tf = doc.getTfMap();
        HashMap<String, Float> tfTest = new HashMap<>();
        tfTest.put("zocaso", 0.4f);
        tfTest.put("gato", 0.3f);
        tfTest.put("mesa", 0.2f);
        tfTest.put("silla", 0.1f);
        assertEquals(tfTest, tf);
    }

    /**
     * Test per comprobar que el vector de tfidf es calcula correctament
     */
    @Test
    public void calculateTfIdf() {
        //Document doc = new Document("test", "Pol", content, docWordCount);
        Document doc = new Document("test", "pol", content);

        HashMap<String, Float> idfMap = new HashMap<>();
        idfMap.put("zocaso", 0.1249f);
        idfMap.put("gato", 0.6020f);
        idfMap.put("mesa", 0.6020f);
        idfMap.put("silla", 0.6020f);

        HashMap<String, Float> tfidf = doc.calculateTfIdf(idfMap);

        // tfidf "zocaso" calculat a mà
        //float tempTest = 0.049975;
        // tfidf "gato" calculat a mà
        float tempTest = 0.18061f;

        float temp = tfidf.get("gato");
        //float tempTest = tfidfTest.get("zocaso");
        assertEquals(tempTest, temp, 0.01);
    }

    /**
     * Test per veure si alguna frase del document compleix una expressió booleana
     */
    @Test
    public void evaluateBooleanExpression() {
        Document doc = new Document("test", "dante", contentBool);
        Node n1 = new StringNode("pastel");
        Node n2 = new StringNode("chocolate");
        Node n3 = new OrNode(n1, n2);
        Node n4 = new StringNode("zocaso");
        Node root = new AndNode(n4, n3);
        // zocaso & pastel | chocolate
        boolean b = doc.evaluateBooleanExpression(root);
        assertTrue(b);
    }

    /**
     * Test per veure si alguna frase del document compleix una expressió booleana
     */
    @Test
    public void evaluateBooleanExpression1() {
        Document doc = new Document("test", "dante", contentBool);
        Node n1 = new StringNode("juan");
        Node n2 = new StringNode("chocolate");
        Node n3 = new OrNode(n1, n2);
        Node n4 = new StringNode("zocaso");
        Node root = new AndNode(n4, n3);
        // zocaso & juan | chocolate
        boolean b = doc.evaluateBooleanExpression(root);
        assertFalse(b);
    }
}