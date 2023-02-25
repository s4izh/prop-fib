package persistenceTests;

import static org.junit.Assert.*;
import persistence.PersistenceController;
import org.junit.Before;
import org.junit.Test;

public class PersistenceControllerTest {
    private PersistenceController controller;

    @Before
    public void setUp() {
        controller = PersistenceController.getInstance();
        controller.iniPersistenceController();
    }
}
