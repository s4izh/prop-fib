package domain.node;

public class AndNode extends BinaryNode {
    /**
     * Creadora d'un AndNode.
     * @param leftExpression  node fill esquerre.
     * @param rightExpression node fill dret.
     */
    public AndNode(Node leftExpression, Node rightExpression) {
        super(leftExpression, rightExpression);
    }

    /**
     * Funció que comprova si la string phrase satisfà l'operació AND de les expressions esquerra i dreta.
     * @param phrase una frase d'un document.
     * @return true si l'expressió es satisfà, false en cas contrari.
     */
    public Boolean Evaluate(String phrase) {
        return getLeftExpression().Evaluate(phrase) && getRightExpression().Evaluate(phrase);
    }

    /*----------------------------- funcions per fer tests --------------------------------*/


    /**
     * Funció que serveix per saber amb quin tipus de node s'està tractant.
     * @return String amb valor "and".
     */
    public String whichType()
    {
        return "and";
    }
}
