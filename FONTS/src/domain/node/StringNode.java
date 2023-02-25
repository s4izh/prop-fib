package domain.node;

public class StringNode extends Node {
    /**
     * Un operand d'una expressió booleana, pot ser una paraula, una subfrase o una frase.
     */
    private final String element;

    /**
     * Creadora d'un StringNode.
     * @param element una string que pot ser una paraula, una subfrase o una frase.
     */
    public StringNode(String element) {
        this.element = element;
    }

    /**
     * Comprova si l'element de la classe està inclòs a la frase passada per paràmetre.
     * @param phrase una frase d'un document.
     * @return true si element es troba a phrase, false en cas contrari.
     */
    public Boolean Evaluate(String phrase) {
        if (!element.contains(" ")) {
            String[] s = phrase.split(" ");
            for (String word : s) {
                if (word.contains(element)) {
                    int wordLength = word.length();
                    int elementLength = element.length();
                    if (wordLength == elementLength)
                    {
                        return true;
                    }
                    else if (Character.isDigit(word.charAt(0)) || Character.isDigit(word.charAt(wordLength-1)))
                    {

                    }
                    else if (  (!Character.isLetter(word.charAt(0)) && wordLength - 1 == elementLength)
                            || (!Character.isLetter(word.charAt(wordLength-1)) && wordLength - 1 == elementLength)
                            || (!Character.isLetter(word.charAt(0)) && !Character.isLetter(word.charAt(wordLength-1)) && wordLength - 2 == elementLength)  )
                    {
                        return true;
                    }
                }
            }
            return false;
        }
        return phrase.contains(element);
    }

    /*----------------------------- funcions per fer tests --------------------------------*/

    /**
     * Funció que serveix per saber amb quin tipus de node s'està tractant.
     * @return L'atribut element.
     */
    public String whichType()
    {
        return element;
    }

    /**
     * Getter del fill esquerre del node.
     * @return null
     */
    public Node getLeftExpression()
    {
        return null;
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