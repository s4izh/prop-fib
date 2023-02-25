package domain.classes;

import domain.node.StringNode;
import domain.node.AndNode;
import domain.node.Node;
import domain.node.NotNode;
import domain.node.OrNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class BooleanExpression implements Serializable {
    /**
     * String amb l'expressió booleana.
     */
    private final String boolExpression;

    /**
     * Integer utilitzat per iterar per l'expressió booleana una vegada pasada a llista.
     */
    private int index;

    /**
     * Node arrel de l'arbre binari que conté l'expressió booleana.
     */
    private Node root;

    /**
     * Creadora de la classe BooleanExpression.
     * @param boolExpression expressió booleana.
     * @throws Exception excepció interna per a la correcta creació de l'arbre.
     */
    public BooleanExpression(String boolExpression) throws Exception {
        this.boolExpression = boolExpression;
        this.index = 0;
        generateTree();
    }

    /**
     * Funció feta per enviar l'arbre a ResultDocument.
     * @param rd el ResultDocument del sistema.
     */
    public void useBooleanExpression(ResultDocuments rd) {
        rd.setDocsBool(root);
    }

    /**
     * Getter de l'expressió booleana.
     * @return l'expressió booleana.
     */
    public String getBoolExpression() {
        return boolExpression;
    }

    /**
     * Getter del node arrel de l'arbre.
     * @return el node arrel de l'arbre.
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Funció que converteix l'atribut boolExpression a una ArrayList de Strings sense els espais innecessaris
     * és pública només per al test.
     * @return un ArrayList de Strings on cada element del Array és un operador, o un operand o un parèntesi.
     */
    public ArrayList<String> getTokens() {
        ArrayList<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int i = 0;

        while (i < boolExpression.length()) {
            char c = boolExpression.charAt(i);
            String s = "" + c;
            ++i;
            switch (c) {
                case ' ':
                    continue;
                case '(':
                case ')':
                case '!':
                    if (sb.length() > 0) {
                        tokens.add(sb.toString());
                        sb.setLength(0);
                    }

                    tokens.add(s);
                    continue;

                case '&':
                case '|':
                    if (sb.length() != 0) {
                        tokens.add(sb.toString());
                        sb.setLength(0);

                    }

                    tokens.add(s);
                    continue;

                case '"':

                    while (boolExpression.charAt(i) != '"') {
                        sb.append(boolExpression.charAt(i));
                        ++i;
                    }
                    tokens.add(sb.toString());
                    sb.setLength(0);
                    ++i;
                    continue;

                case '{':
                    if (sb.length() > 0) {
                        tokens.add(sb.toString());
                        sb.setLength(0);
                    }
                    String aux = "(";
                    tokens.add(aux);

                    while (boolExpression.charAt(i) != '}')
                    {
                        if (boolExpression.charAt(i) == ' ')
                        {
                            if (sb.length() != 0) {
                                tokens.add(sb.toString());
                                sb.setLength(0);

                            }
                            aux = "&";
                            tokens.add(aux);
                        }
                        else
                        {
                            sb.append(boolExpression.charAt(i));
                        }
                        ++i;
                    }
                    if (sb.length() > 0) {
                        tokens.add(sb.toString());
                        sb.setLength(0);
                    }
                    aux = ")";
                    tokens.add(aux);
                    ++i;
                    continue;

                default:
                    sb.append(c);
                    if ( (i+1 < boolExpression.length() && boolExpression.charAt(i) == ' '))
                    {
                        tokens.add(sb.toString());
                        sb.setLength(0);
                    }
            }
        }
        if (sb.length() != 0) {
            tokens.add(sb.toString());
        }

        return tokens;
    }

    /**
     * Funció que genera l'arbre binari que conté l'expressió booleana.
     * @throws Exception excepció interna per a la correcta creació de l'arbre.
     */
    public void generateTree() throws Exception {
        ArrayList<String> tokens = getTokens();
        root = parse(tokens);
    }

    /**
     * Funció encarregada de retornar el node arrel de l'arbre.
     * @param tokens ArrayList de Strings on cada element del Array és un operador, o un operand o un parèntesi de l'expressió booleana.
     * @return el node arrel de l'arbre, que pot ser de qualsevol tipus.
     * @throws Exception excepció interna per a la correcta creació de l'arbre.
     */
    private Node parse(ArrayList<String> tokens) throws Exception {
        return parseExp(tokens, 0);
    }

    /**
     * Funció encarregada de retorna AndNodes i OrNodes.
     * @param tokens ArrayList de Strings on cada element de l'Array és un operador, o un operand o un parèntesi de l'expressió booleana.
     * @param oberts Integer que serveix per controlar si s'ha obert un parèntesi prèviament o no.
     * @return node AND o node OR amb els seus respectius fills.
     * @throws Exception excepció interna per a la correcta creació de l'arbre.
     */
    private Node parseExp(ArrayList<String> tokens, int oberts) throws Exception {
        Node leftExp = parseSubExp(tokens,oberts);
        if (index >= tokens.size())
            return leftExp;
        else if (Objects.equals(tokens.get(index), ")"))
        {
            if (oberts == 0)
            {
                throw new Exception("No s'obre el parèntesi.");
            }
            return leftExp;
        }

        String token = tokens.get(index);

        if (Objects.equals(token, "&")) {
            index++;
            Node rightExp = parseExp(tokens, oberts);
            return new AndNode(leftExp, rightExp);

        } else if (Objects.equals(token, "|")) {
            index++;
            Node rightExp = parseExp(tokens, oberts);
            return new OrNode(leftExp, rightExp);

        } else {
            throw new Exception("L'expressió booleana és incorrecta");
        }
    }

    /**
     * Funció encarregada de retornar NotNodes i StringNodes.
     * @param tokens ArrayList de Strings on cada element del Array és un operador, o un operand o un parèntesi de l'expressió booleana.
     * @param oberts Integer que serveix per controlar si s'ha obert un parèntesi prèviament o no.
     * @return node NOT amb el seu fill o node fulla (node String).
     * @throws Exception excepció interna per a la correcta creació de l'arbre.
     */
    private Node parseSubExp(ArrayList<String> tokens, int oberts) throws Exception {
        String token = tokens.get(index);

        if (Objects.equals(token, "(")) {
            index++;
            Node node = parseExp(tokens, oberts+1);
            if (index == tokens.size())
            {
                throw new Exception ("Els parèntesis no són correctes.");
            }
            index++;
            return node;
        } else if (Objects.equals(token, "!")) {
            index++;
            Node node = parseSubExp(tokens, oberts);
            return new NotNode(node);

        }
        else {
            index++;
            return new StringNode(token);
        }
    }
}