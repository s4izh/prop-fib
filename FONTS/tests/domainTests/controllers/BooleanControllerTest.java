package domainTests.controllers;

import domain.controllers.BooleanController;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BooleanControllerTest {

    /**
     * Test que comprova que s'afegeix una expressió correctament.
     * @throws Exception excepció interna per a la correcta creació de l'arbre.
     */
    @Test
    public void addExpression() throws Exception
    {
        BooleanController bc = new BooleanController();

        bc.addExpression("p1 & p2");

        ArrayList<String> ar = bc.getExpressions();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("p1 & p2");

        assertEquals(ar,expected);
    }

    /**
     * Test que comproba que el Getter d'expressions funciona correctament.
     * @throws Exception excepció interna per a la correcta creació de l'arbre.
     */
    @Test
    public void getExpressions() throws Exception
    {
        BooleanController bc = new BooleanController();

        bc.addExpression("{p1 p2}");

        ArrayList<String> ar = bc.getExpressions();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("{p1 p2}");

        assertEquals(ar,expected);
    }

    /**
     * Test que comprova que la funció que comprova l'existència d'una expressió funciona correctament
     * @throws Exception excepció interna per a la correcta creació de l'arbre.
     */
    @Test
    public void existsExpression() throws Exception
    {
        BooleanController bc = new BooleanController();

        bc.addExpression("{p1 p2}");

        assertFalse(bc.existsExpression("(p1&p3)"));
    }

    /**
     * Test que comprova s'esborri una expressió correctament.
     * @throws Exception excepció interna per a la correcta creació de l'arbre.
     */
    @Test
    public void eraseExpression() throws Exception
    {
        BooleanController bc = new BooleanController();

        bc.addExpression("{p1 p2}");
        bc.eraseExpression("(p1&p2)");

        assertFalse(bc.existsExpression("(p1&p2)"));
    }

    /**
     * Test que comprova que es modifiqui correctament una expressió booleana.
     * @throws Exception excepció interna per a la correcta creació de l'arbre.
     */
    @Test
    public void modifyExpression() throws Exception
    {
        BooleanController bc = new BooleanController();

        bc.addExpression("{p1 p2}");
        bc.modifyExpression("{p1 p2}","{p1 p3}");

        assertTrue(bc.existsExpression("{p1 p3}"));
    }
}