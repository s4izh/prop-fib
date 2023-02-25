package domainTests.node;

import domain.node.AndNode;
import domain.node.StringNode;
import org.junit.Test;

import static org.junit.Assert.*;

public class AndNodeTest {

    /**
     * Test per comprovar si la funció Evaluate és correcte en cas de donar resultat true.
     */
    @Test
    public void EvaluateTrue() {
        StringNode n1 = new StringNode("prop");
        StringNode n2 = new StringNode("par");
        AndNode and = new AndNode(n1, n2);

        String phrase = "prop par as";

        assertTrue(and.Evaluate(phrase));
    }

    /**
     * Test per comprovar si la funció Evaluate és correcte en cas de donar resultat false.
     */
    @Test
    public void EvaluateFalse() {
        StringNode n1 = new StringNode("prop");
        StringNode n2 = new StringNode("par");
        AndNode and = new AndNode(n1, n2);

        String phrase = "prop para as";

        assertFalse(and.Evaluate(phrase));
    }

}