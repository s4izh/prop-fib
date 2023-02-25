package domainTests.node;

import domain.node.Node;
import domain.node.StringNode;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringNodeTest {

    /**
     * Test per comprovar si la funció Evaluate és correcte en cas de donar resultat true.
     */
    @Test
    public void EvaluateTrue() {
        Node leaf = new StringNode("(no");
        String phrase = "par (no, cosa7";

        assertTrue(leaf.Evaluate(phrase));
    }

    /**
     * Test per comprovar si la funció Evaluate és correcte en cas de donar resultat false.
     */
    @Test
    public void EvaluateFalse() {
        Node leaf = new StringNode("pro");
        String phrase = "prop mola";

        assertFalse(leaf.Evaluate(phrase));
    }

}