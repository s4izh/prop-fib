package persistenceTests;

import persistence.JsonManager;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.io.IOException;

public class JsonManagerTest {
    /**
     * Una instància de la classe JsonManager.
     */
    private JsonManager jsonManager;

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
        jsonManager = new JsonManager();
        loadPath = System.getProperty("user.dir") + "/FONTS/tests/persistenceTests/fileTests/youcef--trabsa.json";
        loadBlankTitlePath = System.getProperty("user.dir") + "/FONTS/tests/persistenceTests/fileTests/empty--title.json";
        loadBlankAuthorPath = System.getProperty("user.dir") + "/FONTS/tests/persistenceTests/fileTests/empty--author.json";
        loadBlankContentPath = System.getProperty("user.dir") + "/FONTS/tests/persistenceTests/fileTests/empty--content.json";
        downloadPath = System.getProperty("user.dir") + "/FONTS/tests/persistenceTests/fileTests";
    }

    /**
     * Funció per comprovar el funcionament de la càrrega d'un document json correcte.
     * @throws Exception Quan no s'escriu el títol, l'autor i/o el contingut.
     */
    @Test
    public void testLoadFile() throws Exception {
        String[] expected = {"youcef", "trabsa", "content"};
        String[] result = jsonManager.loadFile(loadPath);
        assertArrayEquals(expected, result);
    }

    /**
     * Funció per comprovar el funcionament de la càrrega d'un document json sense títol.
     */
    @Test
    public void testLoadFileTitleBlank() {
        try {
            jsonManager.loadFile(loadBlankTitlePath);
            fail("Exception was not thrown");
        } catch (Exception e) {
            assertEquals("el títol no pot ser buit", e.getMessage());
        }
    }

    /**
     * Funció per comprovar el funcionament de la càrrega d'un document json sense autor.
     */
    @Test
    public void testLoadFileAuthorBlank() {
        try {
            jsonManager.loadFile(loadBlankAuthorPath);
            fail("Exception was not thrown");
        } catch (Exception e) {
            assertEquals("l'autor no pot ser buit", e.getMessage());
        }
    }

    /**
     * Funció per comprovar el funcionament de la càrrega d'un document json sense contingut.
     */
    @Test
    public void testLoadFileContentBlank() {
        try {
            jsonManager.loadFile(loadBlankContentPath);
            fail("Exception was not thrown");
        } catch (Exception e) {
            assertEquals("el contingut no pot ser buit", e.getMessage());
        }
    }

    /**
     * Funció per comprovar el funcionament de la descàrrega d'un document json.
     * @throws IOException En cas que hi hagi algun error amb la descàrrega.
     */
    @Test
    public void testDownloadFile() throws IOException {
        String title = "download";
        String author = "file";
        String content = "content";
        jsonManager.downloadFile(title, author, content, downloadPath);
    }
}
