package domainTests.node;

import domain.node.OrNode;
import domain.node.StringNode;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrNodeTest {

    /**
     * Test per comprovar si la funció Evaluate és correcte en cas de donar resultat true.
     */
    @Test
    public void EvaluateTrue() {
        StringNode n1 = new StringNode("prop");
        StringNode n2 = new StringNode("par");
        OrNode or = new OrNode(n1, n2);

        String phrase = "par";

        assertTrue(or.Evaluate(phrase));
    }

    /**
     * Test per comprovar si la funció Evaluate és correcte en cas de donar resultat false.
     */
    @Test
    public void EvaluateFalse() {
        StringNode n1 = new StringNode("prop");
        StringNode n2 = new StringNode("par");
        OrNode or = new OrNode(n1, n2);

        String phrase = "para";

        assertFalse(or.Evaluate(phrase));
    }
}