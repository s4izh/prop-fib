package domainTests.node;

import domain.node.Node;
import domain.node.NotNode;
import domain.node.StringNode;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotNodeTest {

    /**
     * Test per veure si dona true quan ha de fer-ho
     */
    @Test
    public void EvaluateTrue() {
        Node fill = new StringNode("prop");
        Node root = new NotNode(fill);
        String phrase = "par no mola";

        assertTrue(root.Evaluate(phrase));
    }

    /**
     * Test per veure si dona fals quan ha de fer-ho
     */
    @Test
    public void EvaluateFalse() {
        Node fill = new StringNode("prop");
        Node root = new NotNode(fill);
        String phrase = "prop mola";

        assertFalse(root.Evaluate(phrase));
    }
}