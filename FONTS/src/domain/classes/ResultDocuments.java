package domain.classes;

import domain.node.Node;

import java.util.ArrayList;

public class ResultDocuments {

    /**
     * ArrayList de documents que es modifica al realitzar consultes.
     */
    private ArrayList<Document> documents;

    /**
     * Funció per inicialitzar el ResultDocuments.
     */
    public ResultDocuments() {
        this.documents = new ArrayList<>();
    }

    /**
     * Funció per assignar-li un nou valor a l'ArrayList de documents.
     * @param v ArrayList de documents nou.
     */
    public void setResult(ArrayList<Document> v) {
        this.documents = v;
    }

    /**
     * Funció per retornar l'ArrayList de documents.
     * @return es retorna una ArrayList de documents.
     */
    public ArrayList<Document> getDocs() {
        return documents;
    }

    /**
     * Funció per ordenar l'ArrayList de documents.
     * @param order indica el mètode d'ordenació.
     */
    public void order(String order) {
        if (order.equals("0")) orderByTitle();
        else if (order.equals("1")) orderByAuthorName();
    }

    /**
     * Funció per filtrar els documents que compleixen que el seu títol comencen per un prefix.
     * @param pref prefix indicat per l'usuari per realitzat la consulta.
     */
    public void getByPrefix(String pref) {
        orderByTitle();
        ArrayList<Document> docsRes = new ArrayList<>();
        int l = 0;
        int r = documents.size() - 1;
        int startIndex = -1;
        while (l <= r) {
            int mid = (r - l) / 2 + l;
            if (documents.get(mid).getTitle().startsWith(pref)) {
                startIndex = mid;
                r = mid - 1;
            } else if (documents.get(mid).getTitle().compareTo(pref) > 0) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        if (startIndex != -1) {
            while (startIndex < documents.size() && documents.get(startIndex).getTitle().startsWith(pref)) {
                docsRes.add(documents.get(startIndex));
                ++startIndex;
            }
        }
        documents = docsRes;
    }

    /**
     * Funció que elimina de l'atribut documents tots els documents que no contenen cap frase que compleixi l'expressió booleana continguda a l'arbre binari amb arrel root.
     * @param root node arrel de l'arbre binari que conté l'expressió booleana.
     */
    public void setDocsBool(Node root) {
        int n = documents.size();
        ArrayList<Document> prov = new ArrayList<>();
        for (Document document : documents) {
            if (document.evaluateBooleanExpression(root)) {
                prov.add(document);
            }
        }
        documents = prov;
    }

    /**
     * Funció per ordenar l'ArrayList pels títols dels documents.
     */
    private void orderByTitle() {
        documents.sort((d1, d2) -> {
            if (d1.getTitle().compareTo(d2.getTitle()) != 0)
                return d1.getTitle().compareTo(d2.getTitle());
            else return d1.getAuthor().compareTo(d2.getAuthor());
        });
    }

    /**
     * Funció per ordenar l'ArrayList pel nom dels autors dels documents.
     */
    private void orderByAuthorName() {
        documents.sort((d1, d2) -> {
            if (d1.getAuthor().compareTo(d2.getAuthor()) != 0)
                return d1.getAuthor().compareTo(d2.getAuthor());
            else return d1.getTitle().compareTo(d2.getTitle());
        });
    }
}