package domain.node;

public class OrNode extends BinaryNode {
    /**
     * Creadora d'un OrNode.
     * @param leftExpression  node fill esquerre.
     * @param rightExpression node fill dret.
     */
    public OrNode(Node leftExpression, Node rightExpression) {
        super(leftExpression, rightExpression);
    }

    /**
     * Funció que comprova si la string phrase satisfà l'operació OR de les expressions esquerra i dreta.
     * @param phrase una frase d'un document.
     * @return true si l'expressió es satisfà, false en cas contrari.
     */
    public Boolean Evaluate(String phrase) {
        return getLeftExpression().Evaluate(phrase) || getRightExpression().Evaluate(phrase);
    }


    /**
     * Funció que serveix per saber amb quin tipus de node s'està tractant.
     * @return String amb valor "or".
     */
    public String whichType()
    {
        return "or";
    }
}