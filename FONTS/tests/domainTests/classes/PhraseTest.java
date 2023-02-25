package domainTests.classes;

import domain.classes.BooleanExpression;
import domain.classes.Phrase;
import domain.node.AndNode;
import domain.node.Node;
import domain.node.OrNode;
import domain.node.StringNode;
import org.junit.Test;

import static org.junit.Assert.*;

public class PhraseTest {

    /**
     * Comprova que s'obté una Phrase.
     */
    @Test
    public void getPhrase() {
        Phrase phr = new Phrase("hola como estamos");

        assertEquals("hola como estamos", phr.getPhrase());
    }

    /**
     * Comprova si una Phrase compleix una expressió booleana.
     */
    @Test
    public void evaluate() throws Exception
    {
        Phrase p = new Phrase("p3.");
        Node n1 = new StringNode("p3");
        Node n2 = new StringNode("p1");
        Node root = new AndNode(n1,n2);

        BooleanExpression b = new BooleanExpression("!(p1 & p2) | p3");
        String s = b.getRoot().whichType();
        //assertEquals("not",s);
        assertEquals(true,p.evaluate(b.getRoot()));
    }

    /**
     * Comprova si una Phrase no compleix una expressió booleana.
     */
    @Test
    public void evaluate2()
    {
        Phrase p = new Phrase("p1 p2 p3.");
        Node n1 = new StringNode("juan");
        Node n2 = new StringNode("prop");
        Node root = new OrNode(n1,n2);

        assertEquals(false,p.evaluate(root));
    }

    /**
     * Comprova si una Phrase compleix una expressió booleana.
     */
    @Test
    public void evaluate3()
    {
        Phrase p = new Phrase("p1 p2 p3.");
        Node n1 = new StringNode("juan");
        Node n2 = new StringNode("p2");
        Node n3 = new StringNode("p3");
        Node root = new AndNode(n1,n2);
        Node root2 = new OrNode(root,n3);

        assertEquals(true,p.evaluate(root2));
    }

}