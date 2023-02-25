package domain.node;

public abstract class BinaryNode extends Node {
    /**
     * Node fill esquerre.
     */
    private final Node leftExpression;

    /**
     * Node fill dret.
     */
    private final Node rightExpression;

    /**
     * Creadora d'un node binary.
     * @param leftExpression  fill esquerre.
     * @param rightExpression fill dret.
     */
    public BinaryNode(Node leftExpression, Node rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    /**
     * Getter del fill esquerre del node.
     * @return node fill esquerre.
     */
    public Node getLeftExpression() {
        return leftExpression;
    }

    /**
     * Getter del fill dret del node.
     * @return node fill dret.
     */
    public Node getRightExpression() {
        return rightExpression;
    }
}
