package domainTests.classes;

import domain.classes.BooleanExpression;
import domain.node.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.*;

public class BooleanExpressionTest {

    /**
     * Test que comprova el bon funcionament del getter de boolExpression.
     * @throws Exception excepció interna per a la correcta creació de l'arbre
     */
    @Test
    public void getBooleanExpression() throws Exception
    {
        BooleanExpression b = new BooleanExpression("prop");

        assertEquals("prop",b.getBoolExpression());
    }

    /**
     * Test que comprova la correcta creació de la llista de strings de l'expressió booleana.
     * @throws Exception excepció interna per a la correcta creació de l'arbre
     */
    @Test
    public void getTokens() throws Exception
    {
        BooleanExpression b = new BooleanExpression("{prop par} & as");

        ArrayList<String> tokens = b.getTokens();
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("(");
        expected.add("prop");
        expected.add("&");
        expected.add("par");
        expected.add(")");
        expected.add("&");
        expected.add("as");

        assertEquals(expected,tokens);
    }

    private boolean compareTrees(Node n1, Node n2)
    {
        if (n1 == null && n2 == null)
            return true;
        else if (n1 != null && n2 != null)
        {
            return (Objects.equals(n1.whichType(),n2.whichType())
                    && compareTrees(n1.getLeftExpression(),n2.getLeftExpression())
                    && compareTrees(n1.getRightExpression(),n2.getRightExpression())) ;
        }
        return false;
    }

    /**
     * Test que comprova la correcta generació de l'arbre binari que conté l'expressió booleana.
     * @throws Exception excepció interna per a la correcta creació de l'arbre
     */
    @Test
    public void generateTree() throws Exception
    {
        BooleanExpression b = new BooleanExpression("!(p1 & p2) | p3");

        // !(p1 & p2)
        Node n1 = new StringNode("p1");
        Node n2 = new StringNode("p2");
        Node and = new AndNode(n1,n2);
        Node not = new NotNode(and);
        // !(p1 & p2) | p3
        Node n3 = new StringNode("p3");
        Node or = new OrNode(not,n3);

        assertTrue(compareTrees(or,b.getRoot()));
    }

}