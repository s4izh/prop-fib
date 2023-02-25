package domain.node;

public abstract class Node {
    /**
     * Funció abstracta per evaluar si el paràmetre phrase compleix l'expressió del node.
     * @param phrase una frase d'un document
     * @return true si la frase compleix l'expressió, fals en cas contrari.
     */
    public abstract Boolean Evaluate(String phrase);

    /**
     * Funció abstracta que retorna el fill esquerre del node.
     * @return node fill esquerre
     */
    public abstract Node getLeftExpression();

    /**
     * Funció abstracta que retorna el fill dret del node.
     * @return node fill dret
     */
    public abstract Node getRightExpression();


    /*----------------------------- funcions per fer tests --------------------------------*/

    /**
     * Funció que serveix per saber amb quin tipus de node s'està tractant.
     * @return Una string amb possibles valors. En el cas que es tracti d'un AndNode retorna "and", si és un OrNode retorna "or",
     * NotNode retorna "not" i StringNode retorna l'element que conté que pot ser qualsevol string.
     */
    public abstract String whichType ();

}