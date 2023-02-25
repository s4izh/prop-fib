package domain.controllers;

import domain.classes.BooleanExpression;
import domain.classes.ResultDocuments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class BooleanController {
    /**
     * HashMap de les expressions booleanes al sistema.
     */
    private final HashMap<String, BooleanExpression> expressions;

    /**
     * Constructora del BooleanController.
     */
    public BooleanController() {
        expressions = new HashMap<>();
    }

    /**
     * Funció per afegir una nova expressió booleana al sistema.
     * @param exp Expressió que ha escrit l'usuari.
     * @throws Exception Quan no es crea correctament una expressió booleana.
     */
    public void addExpression(String exp) throws Exception {
        BooleanExpression b = new BooleanExpression(exp);
        expressions.put(exp, b);
    }

    /**
     * Funció per utilitzar una expressió booleana.
     * @param exp    expressió booleana.
     * @param resDoc instància de la classe ResultDocuments.
     */
    public void useBooleanExpression(String exp, ResultDocuments resDoc) {
        expressions.get(exp).useBooleanExpression(resDoc);
    }

    /**
     * Funció per eliminar una expressió booleana.
     * @param exp expressió booleana.
     */
    public void eraseExpression(String exp) {
        expressions.remove(exp);
    }

    /**
     * Funció per comprovar si existeix una funció booleana.
     * @param exp expressió booleana.
     * @return boolean que retorna false si no existeix l'expressió o true en el cas contrari.
     */
    public Boolean existsExpression(String exp) {
        return expressions.containsKey(exp);
    }

    /**
     * Funció per modificar una expressió booleana.
     * @param oldExp expressió booleana antiga.
     * @param newExp nova expressió booleana.
     * @throws Exception controla la correcta creació de les expressions booleanes.
     */
    public void modifyExpression(String oldExp, String newExp) throws Exception {
        eraseExpression(oldExp);
        addExpression(newExp);
    }

    /**
     * Funció per retornar les expressions booleanes existents al sistema.
     * @return retorna una ArrayList de strings de totes les expressions booleanes.
     */
    public ArrayList<String> getExpressions() {
        ArrayList<String> exp = new ArrayList<>();
        for (Entry<String, BooleanExpression> entry : expressions.entrySet()) {
            String aux = entry.getKey();
            exp.add(aux);
        }
        return exp;
    }

    /**
     * Funció per recarregar les expressions booleanes utilitzades en una sessió anterior.
     * @param booleanExpressions ArrayList amb les expressions booleanes.
     * @throws Exception controla la correcta creació de les expressions booleanes.
     */
    public void reloadExpressions(ArrayList<String> booleanExpressions) throws Exception {
        for (String s : booleanExpressions) {
            addExpression(s);
        }
    }

    /**
     * Funció per saber quantes expressions booleanes hi ha al sistema.
     * @return retorna el nombre d'expressions.
     */
    public int size() {
        return expressions.size();
    }


}
