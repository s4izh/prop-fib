package domain.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DummyData implements Serializable {

    /**
     * HashMap amb totes les paraules que apareixen en tots els documents i quantes vegades apareix cadascuna.
     */
    public final HashMap<String, Integer> globalWordCount;

    /**
     * ArrayList amb totes les expressions booleanes guardades per l'usuari.
     */
    public final ArrayList<String> booleanExpressions;

    /**
     * Creadora.
     * @param globalWordCount HashMap amb les paraules de tots els documents i quantes vegades apareixen.
     * @param booleanExpressions ArrayList de les expressions booleanes.
     */
    public DummyData(HashMap<String, Integer> globalWordCount, ArrayList<String> booleanExpressions) {
        this.globalWordCount = globalWordCount;
        this.booleanExpressions = booleanExpressions;
    }
}
