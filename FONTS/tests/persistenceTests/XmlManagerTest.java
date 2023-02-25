package persistenceTests;

import persistence.XmlManager;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.io.IOException;

public class XmlManagerTest {

    /**
     * Una instància de la classe XmlManager.
     */
    private XmlManager xmlManager;

    /**
     * Path utilitzat per carregar un document correcte.
     */
    private String loadPath;

    /**
     * Path utilitzat per carregar un document sense títol.
     */
    private String loadBlankTitlePath;

    /**
     * Path utilitzat per carregar un document sense autor.
     */
    private String loadBlankAuthorPath;

    /**
     * Path utilitzat per carregar un document sense contingut.
     */
    private String loadBlankContentPath;

    /**
     * Path utilitzat per fer la descàrrega d'un document.
     */
    private String downloadPath;

    /**
     * Funció que dona valor als atributs anteriors.
     */
    @Before
    public void setUp() {
        xmlManager = new XmlManager();
        loadPath = System.getProperty("user.dir") + "/FONTS/tests/persistenceTests/fileTests/youcef--trabsa.xml";
        loadBlankTitlePath = System.getProperty("user.dir") + "/FONTS/tests/persistenceTests/fileTests/empty--title.xml";
        loadBlankAuthorPath = System.getProperty("user.dir") + "/FONTS/tests/persistenceTests/fileTests/empty--author.xml";
        loadBlankContentPath = System.getProperty("user.dir") + "/FONTS/tests/persistenceTests/fileTests/empty--content.xml";
        downloadPath = System.getProperty("user.dir") + "/FONTS/tests/persistenceTests/fileTests";
    }

    /**
     * Funció per comprovar el funcionament de la càrrega d'un document xml correcte.
     * @throws Exception Quan no s'escriu el títol, l'autor i/o el contingut.
     */
    @Test
    public void testLoadFile() throws Exception {
        String[] expected = {"youcef", "trabsa", "content"};
        String[] result = xmlManager.loadFile(loadPath);
        assertArrayEquals(expected, result);
    }

    /**
     * Funció per comprovar el funcionament de la càrrega d'un document xml sense títol.
     */
    @Test
    public void testLoadFileTitleBlank() {
        try {
            xmlManager.loadFile(loadBlankTitlePath);
            fail("Exception was not thrown");
        } catch (Exception e) {
            assertEquals("el títol no pot ser buit", e.getMessage());
        }
    }

    /**
     * Funció per comprovar el funcionament de la càrrega d'un document xml sense autor.
     */
    @Test
    public void testLoadFileAuthorBlank() {
        try {
            xmlManager.loadFile(loadBlankAuthorPath);
            fail("Exception was not thrown");
        } catch (Exception e) {
            assertEquals("l'autor no pot ser buit", e.getMessage());
        }
    }

    /**
     * Funció per comprovar el funcionament de la càrrega d'un document xml sense contingut.
     */
    @Test
    public void testLoadFileContentBlank() {
        try {
            xmlManager.loadFile(loadBlankContentPath);
            fail("Exception was not thrown");
        } catch (Exception e) {
            assertEquals("el contingut no pot ser buit", e.getMessage());
        }
    }

    /**
     * Funció per comprovar el funcionament de la descàrrega d'un document xml.
     * @throws IOException En cas que hi hagi algun error amb la descàrrega.
     */
    @Test
    public void testDownloadFile() throws IOException {
        String title = "download";
        String author = "file";
        String content = "content";
        xmlManager.downloadFile(title, author, content, downloadPath);
    }
}