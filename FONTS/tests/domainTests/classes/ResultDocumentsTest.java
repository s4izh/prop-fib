package domainTests.classes;

import domain.classes.Document;
import domain.classes.ResultDocuments;
import domain.node.Node;
import domain.node.AndNode;
import domain.node.StringNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ResultDocumentsTest {

    String content = "zocaso gato mesa.";
    String content2  = "malalteo que te veo.";

    /**
     * Reassigna els documents de ResultDocuments a uns indicats per paràmetre.
     */
    @Test
    public void setResult() {
        ResultDocuments resDoc = new ResultDocuments();
        ArrayList<Document> docs = new ArrayList<>();
        Document doc = new Document("hey", "pol", content);
        Document doc2 = new Document("hey", "paco", content);
        Document doc3 = new Document("hey", "marc", content);
        Document doc4 = new Document("hey", "zac", content);
        Document doc5 = new Document("hey", "p", content);
        docs.add(doc);
        docs.add(doc2);
        docs.add(doc3);
        docs.add(doc4);
        docs.add(doc5);
        resDoc.setResult(docs);

        assertEquals(docs,resDoc.getDocs());
    }

    /**
     * Obté els documents que pertanyen al ResultDocuments.
     */
    @Test
    public void getDocs() {
        ResultDocuments resDoc = new ResultDocuments();
        ArrayList<Document> docs = new ArrayList<>();
        Document doc = new Document("hey", "pol", content);
        Document doc2 = new Document("hey", "paco", content);
        Document doc3 = new Document("hey", "marc", content);
        Document doc4 = new Document("hey", "zac", content);
        Document doc5 = new Document("hey", "p", content);
        docs.add(doc);
        docs.add(doc2);
        docs.add(doc3);
        docs.add(doc4);
        docs.add(doc5);
        resDoc.setResult(docs);

        assertEquals(docs,resDoc.getDocs());
    }

    /**
     * Comprova que obté els documents ordenats per títol.
     */
    @Test
    public void orderByTitle() {
        ResultDocuments resDoc = new ResultDocuments();
        ArrayList<Document> docs = new ArrayList<>();
        Document doc = new Document("e", "pol", content);
        Document doc2 = new Document("d", "paco", content);
        Document doc3 = new Document("c", "marc", content);
        Document doc4 = new Document("b", "zac", content);
        Document doc5 = new Document("a", "p", content);
        docs.add(doc5);
        docs.add(doc4);
        docs.add(doc3);
        docs.add(doc2);
        docs.add(doc);
        resDoc.setResult(docs);
        resDoc.order("0");
        assertEquals(docs,resDoc.getDocs());
    }

    /**
     * Comprova que obté els documents ordenats pel nom de l'autor.
     */
    @Test
    public void orderByAuthorName() {
        ResultDocuments resDoc = new ResultDocuments();
        ArrayList<Document> docs = new ArrayList<>();
        Document doc = new Document("hey", "e", content);
        Document doc2 = new Document("hey", "d", content);
        Document doc3 = new Document("hey", "c", content);
        Document doc4 = new Document("hey", "b", content);
        Document doc5 = new Document("hey", "a", content);
        docs.add(doc5);
        docs.add(doc4);
        docs.add(doc3);
        docs.add(doc2);
        docs.add(doc);
        resDoc.setResult(docs);
        resDoc.order("2");
        assertEquals(docs,resDoc.getDocs());
    }

    /**
     * Comprova que s'obtenen els documents tals que el seu títol comença per un prefix buit, per tant, tots.
     */
    @Test
    public void getByEmptyPrefix() {
        ResultDocuments resDoc = new ResultDocuments();
        ArrayList<Document> docs = new ArrayList<>();
        Document doc = new Document("hey", "e", content);
        Document doc2 = new Document("we", "d", content);
        Document doc3 = new Document("love", "c", content);
        Document doc4 = new Document("prop", "b", content);
        Document doc5 = new Document("fib", "a", content);
        docs.add(doc);
        docs.add(doc2);
        docs.add(doc3);
        docs.add(doc4);
        docs.add(doc5);
        resDoc.setResult(docs);

        resDoc.getByPrefix("");
        assertEquals(docs,resDoc.getDocs());
    }

    /**
     * Comprova que s'obtenen els documents tals que el seu títol comença per un prefix normal.
     */
    @Test
    public void getByCommonPrefix() {
        ResultDocuments resDoc = new ResultDocuments();
        ArrayList<Document> docs = new ArrayList<>();
        Document doc = new Document("hey", "e", content);
        Document doc2 = new Document("we", "d", content);
        Document doc3 = new Document("love", "c", content);
        Document doc4 = new Document("prop", "b", content);
        Document doc5 = new Document("fib", "a", content);
        docs.add(doc);
        docs.add(doc2);
        docs.add(doc3);
        docs.add(doc4);
        docs.add(doc5);
        resDoc.setResult(docs);

        resDoc.getByPrefix("l");
        ArrayList<Document> output = new ArrayList<>();
        output.add(doc3);
        assertEquals(output,resDoc.getDocs());
    }

    /**
     * Comprova el funcionament de la funció setDocsBool.
     */
    @Test
    public void setDocsBool ()
    {
        ResultDocuments resDoc = new ResultDocuments();
        ArrayList<Document> docs = new ArrayList<>();
        ArrayList<Document> expected = new ArrayList<>();

        Document doc = new Document("hey", "e", content);
        Document doc2 = new Document("we", "d", content2);
        Document doc3 = new Document("love", "c", content);
        Document doc4 = new Document("prop", "b", content2);
        Document doc5 = new Document("fib", "a", content);
        docs.add(doc);
        docs.add(doc2);
        docs.add(doc3);
        docs.add(doc4);
        docs.add(doc5);
        resDoc.setResult(docs);

        Node n1 = new StringNode("que");
        Node n2 = new StringNode("te");
        Node and = new AndNode(n1,n2);

        expected.add(doc2);
        expected.add(doc4);

        // boolean expression = que & te
        resDoc.setDocsBool(and);

        assertEquals(expected,resDoc.getDocs());

    }
}