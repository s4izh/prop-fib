package domainTests.controllers;

import domain.classes.Author;
import domain.classes.Document;
import domain.controllers.AuthorController;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class AuthorControllerTest {

    String content = "zocaso gato mesa.";

    /**
     * Comprova que s'afegeix un autor.
     */
    @Test
    public void addAuthor() {
        AuthorController auCx = new AuthorController();
        Document doc = new Document("hola", "pol", content);
        auCx.addAuthor("pol", doc);

        assertTrue(auCx.size() == 1);
    }

    /**
     * Comprova que s'afegeix un document.
     */
    @Test
    public void addDocument() {
        AuthorController auCx = new AuthorController();
        Document doc = new Document("hola", "pol", content);
        Document doc2 = new Document("b", "pol", content);
        auCx.addAuthor("pol", doc);
        auCx.addDocument("pol", doc2);
        Author au = auCx.getAuthor("pol");

        assertTrue(au.getNumberDocs() == 2);
    }

    /**
     * Comprova que es retorna la posició d'un element a l'ArrayList.
     */
    @Test
    public void findExistingElement() {
        AuthorController auCx = new AuthorController();
        Document doc = new Document("hey", "pol", content);
        Document doc2 = new Document("hey", "paco", content);
        Document doc3 = new Document("hey", "marc", content);
        Document doc4 = new Document("hey", "zac", content);
        Document doc5 = new Document("hey", "p", content);
        Document doc6 = new Document("hey", "pa", content);
        Document doc7 = new Document("hey", "pepe", content);
        Document doc8 = new Document("hey", "pipo", content);
        Document doc9 = new Document("hey", "oriol", content);
        Document doc10 = new Document("hey", "paa", content);

        auCx.addAuthor("pol", doc);
        auCx.addAuthor("p", doc5);
        auCx.addAuthor("pa", doc6);
        auCx.addAuthor("paco", doc2);
        auCx.addAuthor("pepe", doc7);
        auCx.addAuthor("pipo", doc8);
        auCx.addAuthor("marc", doc3);
        auCx.addAuthor("oriol", doc9);
        auCx.addAuthor("paa", doc10);
        auCx.addAuthor("zac", doc4);

        Integer input = 8;
        assertEquals(input, auCx.find("pol"));
    }

    /**
     * Comprova que es retorna una posició negativa d'un element que no es troba a l'ArrayList.
     */
    @Test
    public void findUnexistingElement() {
        AuthorController auCx = new AuthorController();
        Document doc = new Document("hey", "pol", content);
        Document doc2 = new Document("hey", "paco", content);
        Document doc3 = new Document("hey", "marc", content);
        Document doc4 = new Document("hey", "zac", content);
        Document doc5 = new Document("hey", "p", content);
        Document doc6 = new Document("hey", "pa", content);
        Document doc7 = new Document("hey", "pepe", content);
        Document doc8 = new Document("hey", "pipo", content);
        Document doc9 = new Document("hey", "oriol", content);
        Document doc10 = new Document("hey", "paa", content);

        auCx.addAuthor("pol", doc);
        auCx.addAuthor("p", doc5);
        auCx.addAuthor("pa", doc6);
        auCx.addAuthor("paco", doc2);
        auCx.addAuthor("pepe", doc7);
        auCx.addAuthor("pipo", doc8);
        auCx.addAuthor("marc", doc3);
        auCx.addAuthor("oriol", doc9);
        auCx.addAuthor("paa", doc10);
        auCx.addAuthor("zac", doc4);

        Integer input = -1;
        assertEquals(input, auCx.find("tinoco"));
    }

    /**
     * Comprova que s'elimina un document d'un autor.
     */
    @Test
    public void deleteDocument() {
        AuthorController auCx = new AuthorController();
        Document doc = new Document("hola", "pol", content);
        Document doc2 = new Document("b", "pol", content);
        auCx.addAuthor("pol", doc);
        auCx.addDocument("pol", doc2);
        auCx.deleteDocument("pol", doc2);
        Author au = auCx.getAuthor("pol");

        assertTrue(au.getNumberDocs() == 1);
    }

    /**
     * Comprova que s'elimina un autor quan es queda sense documents.
     */
    @Test
    public void deleteLastDocument() {
        AuthorController auCx = new AuthorController();
        Document doc = new Document("hola", "pol", content);
        auCx.addAuthor("pol", doc);
        auCx.deleteDocument("pol", doc);
        assertTrue(!auCx.authorExists("pol"));
    }

    /**
     * Comprova que es retorna un autor existent al sistema.
     */
    @Test
    public void getAuthor() {
        AuthorController auCx = new AuthorController();
        Document doc = new Document("hola", "pol", content);
        auCx.addAuthor("pol", doc);
        auCx.addAuthor("marta", doc);
        auCx.addAuthor("diego", doc);
        auCx.addAuthor("dembele", doc);
        auCx.addAuthor("yousep", doc);
        auCx.addAuthor("sergi", doc);
        auCx.addAuthor("messi", doc);
        auCx.addAuthor("a", doc);

        Author au = new Author("a", doc);
        assertEquals(au.getName(), auCx.getAuthor("a").getName());
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

        assertEquals(input, auCx.getTitles("pol"));
    }

    /**
     * Comprova que s'obtenen els autors a partir d'un prefix comú.
     */
    @Test
    public void getAuthorsTypical() {
        ArrayList<String> input = new ArrayList<String>();
        input.add("p");
        input.add("pa");
        input.add("paa");
        input.add("paco");
        input.add("pepe");
        input.add("pipo");
        input.add("pol");

        AuthorController auCx = new AuthorController();
        Document doc = new Document("hey", "pol", content);
        Document doc2 = new Document("hey", "paco", content);
        Document doc3 = new Document("hey", "marc", content);
        Document doc4 = new Document("hey", "zac", content);
        Document doc5 = new Document("hey", "p", content);
        Document doc6 = new Document("hey", "pa", content);
        Document doc7 = new Document("hey", "pepe", content);
        Document doc8 = new Document("hey", "pipo", content);
        Document doc9 = new Document("hey", "oriol", content);
        Document doc10 = new Document("hey", "paa", content);

        auCx.addAuthor("pol", doc);
        auCx.addAuthor("p", doc5);
        auCx.addAuthor("pa", doc6);
        auCx.addAuthor("paco", doc2);
        auCx.addAuthor("pepe", doc7);
        auCx.addAuthor("pipo", doc8);
        auCx.addAuthor("marc", doc3);
        auCx.addAuthor("oriol", doc9);
        auCx.addAuthor("paa", doc10);
        auCx.addAuthor("zac", doc4);

        assertEquals(input, auCx.getAuthors("p"));
    }

    /**
     * Comprova que s'obtenen els autors a partir d'un prefix buit.
     */
    @Test
    public void getAuthorsEmptyPrefix() {
        //Empty prefix, expected: vector with all the authors
        ArrayList<String> input = new ArrayList<String>();
        input.add("marc");
        input.add("paco");
        input.add("pol");
        input.add("zac");

        AuthorController auCx = new AuthorController();
        Document doc = new Document("hey", "pol", content);
        Document doc2 = new Document("hey", "paco", content);
        Document doc3 = new Document("hey", "marc", content);
        Document doc4 = new Document("hey", "zac", content);

        auCx.addAuthor("pol", doc);
        auCx.addAuthor("paco", doc2);
        auCx.addAuthor("marc", doc3);
        auCx.addAuthor("zac", doc4);

        assertEquals(input, auCx.getAuthors(""));
    }

    /**
     * Comprova que un autor existeix.
     */
    @Test
    public void authorExistsTrue() {
        AuthorController auCx = new AuthorController();
        Document doc = new Document("hola", "pol", content);
        auCx.addAuthor("pol", doc);
        assertTrue(auCx.authorExists("pol"));
    }

    /**
     * Comprova que un autor no existeix.
     */
    @Test
    public void authorExistsFalse() {
        AuthorController auCx = new AuthorController();
        Document doc = new Document("hola", "pol", content);
        auCx.addAuthor("pol", doc);
        assertTrue(!auCx.authorExists("salchipapa"));
    }

    /**
     * Comprova que s'obtenen els autors a partir d'un prefix que no es correspon a cap autor.
     */
    @Test
    public void getAuthorsUnmatchingPrefix() {
        //Prefix that doesn't match any author, expected: empty vector
        ArrayList<String> input = new ArrayList<String>();

        AuthorController auCx = new AuthorController();
        Document doc = new Document("hey", "pol", content);
        Document doc2 = new Document("hey", "paco", content);
        Document doc3 = new Document("hey", "marc", content);
        Document doc4 = new Document("hey", "zac", content);

        auCx.addAuthor("pol", doc);
        auCx.addAuthor("paco", doc2);
        auCx.addAuthor("marc", doc3);
        auCx.addAuthor("zac", doc4);

        assertEquals(input, auCx.getAuthors("toni"));
    }


    /**
     * Comprova que el número d'autors al sistema es correspon al que toca.
     */
    @Test
    public void size() {
        AuthorController auCx = new AuthorController();
        Document doc = new Document("hola", "pol", content);
        auCx.addAuthor("pol", doc);

        assertTrue(auCx.size() == 1);
    }
}