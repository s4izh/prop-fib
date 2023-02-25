package domainTests.controllers;

import domain.controllers.DocumentController;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class DocumentControllerTest {

    // ------ continguts simples utilizats ------
    String content = "zocaso gato mesa";
    String content2 = "dante pol mesa";

    // ------------------------------------------

    /**
     * Test per comprovar que existeix un document
     */
    @Test
    public void existsDocumentTrue() {
        DocumentController dcx = new DocumentController();
        dcx.addDocument("test", "sergio", content);
        assertTrue(dcx.existsDocument("test", "sergio"));
    }

    /**
     * Test per comprovar que no existeix un document
     */
    @Test
    public void existsDocumentFalse() {
        DocumentController dcx = new DocumentController();
        dcx.addDocument("test", "sergio", content);
        assertFalse(dcx.existsDocument("no existo", "sergio"));
    }

    /**
     * Test per comprovar que un document s'afegeix correctament
     */
    @Test
    public void addDocument() {
        DocumentController dcx = new DocumentController();
        dcx.addDocument("test", "sergio", content);
        assertEquals(1, dcx.size());
    }

    /**
     * Test per comprovar que un document s'esborra correctament
     */
    @Test
    public void deleteDocument() {
        DocumentController dcx = new DocumentController();
        dcx.addDocument("test", "sergio", content);
        dcx.deleteDocument("test", "sergio");
        assertFalse(dcx.existsDocument("test", "sergio"));
    }

    /**
     * Test per comprovar que la freqüència inversa de les paraules de tots els Documents es
     * calcula correctament
     */
    @Test
    public void calculateIDF() {
        DocumentController dcx = new DocumentController();

        String content1 = "gato perro zoco oca.";
        String content2 = "gato perro zoco.";
        String content3 = "gato perro.";
        String content4 = "gato.";

        dcx.addDocument("test1", "sergio", content1);
        dcx.addDocument("test2", "sergio", content2);
        dcx.addDocument("test3", "sergio", content3);
        dcx.addDocument("test4", "sergio", content4);

        dcx.calculateIdf();

        HashMap<String, Float> idf = dcx.getIdfMap();

        float gatoIdf = idf.get("gato");
        float perroIdf = idf.get("perro");
        float zocoIdf = idf.get("zoco");
        float ocaIdf = idf.get("oca");

        // calcular manualmente idf

        assertEquals(0, gatoIdf, 0.01);
        assertEquals(0.12, perroIdf, 0.01);
        assertEquals(0.3, zocoIdf, 0.01);
        assertEquals(0.6, ocaIdf, 0.01);
    }

    /**
     * Afegeix dos documents (amb paraules en comú) comprova que wordCount
     * s'hagi actualitzat correctament
     */
    @Test
    public void recalculateWordCount() {
        DocumentController dcx = new DocumentController();

        dcx.addDocument("test1", "sergio", content);
        dcx.addDocument("test2", "sergio", content);

        HashMap<String, Integer> wordCount = dcx.getWordCount();

        int zocasoWordCount = wordCount.get("zocaso");
        // com la paraula "zocaso" apareix a dos documents a hauria de ser igual a 2
        assertEquals(2, zocasoWordCount);
    }

    /**
     * Afegeix dos documents (amb paraules en comú), esborra un i comproba que wordCount
     * s'hagi actualitzat correctament
     */
    @Test
    public void recalculateWordCount2() {
        DocumentController dcx = new DocumentController();

        dcx.addDocument("test1", "sergio", content);
        dcx.addDocument("test2", "sergio", content2);

        dcx.deleteDocument("test1", "sergio");

        HashMap<String, Integer> wordCount = dcx.getWordCount();
        int a = wordCount.get("mesa");
        assertEquals(1, a);
    }
}
