package domain.classes;

import domain.node.Node;


/**
 * @author Sergio Sanz Martínez
 */
public class Phrase {
    /**
     * String amb totes les paraules de la frase.
     */
    private final String phrase;

    /**
     * Constructora de la clase Phrase.
     * @param phrase String amb la frase.
     */
    public Phrase(String phrase) {
        this.phrase = phrase;
    }

    /**
     * Funció per obtenir el String que forma la frase.
     * @return String phrase.
     */
    public String getPhrase() {
        return phrase;
    }

    /**
     * Funció que serveix per saber si l'atribut phrase compleix l'expressió booleana continguda en l'arbre binari amb node arrel root.
     * @param root node arrel de l'arbre de l'expressió booleana.
     * @return True si es compleix l'expressió, false si no.
     */
    public Boolean evaluate(Node root) {
        StringBuilder s = new StringBuilder(phrase);
        s.deleteCharAt(phrase.length() - 1);
        return root.Evaluate(s.toString());
    }
}