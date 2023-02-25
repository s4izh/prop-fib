package domainTests.classes;

import domain.classes.Author;
import domain.classes.Document;
import domain.controllers.AuthorController;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class AuthorTest {
    String content = "zocaso gato mesa.";

    /**
     * Comprova que s'afegeix un document correctament.
     */
    @Test
    public void addDocument() {
        Document doc = new Document("a", "pol", content);
        Document doc2 = new Document("b", "pol", content);
        Author au = new Author("pol", doc);
        au.addDocument(doc2);
        assertTrue(au.getNumberDocs() == 2);
    }

    /**
     * Comprova que s'elimina un document correctament.
     */
    @Test
    public void deleteDocument() {
        Document doc = new Document("a", "pol", content);
        Document doc2 = new Document("b", "pol", content);
        Author au = new Author("pol", doc);
        au.addDocument(doc2);
        au.deleteDocument(doc2);
        assertTrue(au.getNumberDocs() == 1);
    }

    /**
     * Comprova que s'obtenen els documents d'un autor.
     */
    @Test
    public void getTitles() {
        ArrayList<Document> input = new ArrayList<Document>();
        Document doc = new Document("hola", "pol", content);
        Document doc2 = new Document("que", "pol", content);
        Document doc3 = new Document("tal", "pol", content);

        input.add(doc);
        input.add(doc2);
        input.add(doc3);

        AuthorController auCx = new AuthorController();

        auCx.addAuthor("pol", doc);
        Author au = auCx.getAuthor("pol");
        au.addDocument(doc2);
        au.addDocument(doc3);

        assertEquals(input, au.getTitles());
    }

    /**
     * Comprova que s'obté el nom d'un autor.
     */
    @Test
    public void getName() {
        Document doc = new Document("a", "pol", content);
        Author au = new Author("pol", doc);
        assertEquals("pol", au.getName());
    }

    /**
     * Comprova que s'obté el número de documents d'un autor.
     */
    @Test
    public void getNumberDocs() {
        Document doc = new Document("a", "pol", content);
        Author au = new Author("pol", doc);
        assertTrue(au.getNumberDocs() == 1);
    }
}