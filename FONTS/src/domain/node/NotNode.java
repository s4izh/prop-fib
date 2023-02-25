package domain.node;

public class NotNode extends Node {
    /**
     * Node fill
     */
    private final Node expression;

    /**
     * Creadora d'un NotNode.
     * @param expression Node fill.
     */
    public NotNode(Node expression) {
        this.expression = expression;
    }

    /**
     * Funció que comprova si la string phrase satisfà l'expressió negada.
     * @param phrase una frase d'un document
     * @return true si l'expressió es satisfà, false en cas contrari.
     */
    public Boolean Evaluate(String phrase) {
        return !(expression.Evaluate(phrase));
    }


    /*----------------------------- funcions per fer tests --------------------------------*/

    /**
     * Funció que serveix per saber amb quin tipus de node s'està tractant.
     * @return String amb valor "not".
     */
    public String whichType()
    {
        return "not";
    }

    /**
     * Getter del fill esquerre del node.
     * @return l'atribut expression
     */
    public Node getLeftExpression()
    {
        return expression;
    }

    /**
     * Getter del fill dreta del node.
     * @return null
     */
    public Node getRightExpression()
    {
        return null;
    }
}