package domain.classes;

import java.util.ArrayList;
import java.util.Comparator;

public class Author {
    /**
     * Nom de l'autor.
     */
    private final String name;

    /**
     * Arraylist dels documents d'un autor.
     */
    private final ArrayList<Document> documents;

    /**
     * Crea un Author i associa el corresponent document.
     * @param name Nom de l'autor vàlid.
     * @param d    Document d existent.
     */
    public Author(String name, Document d) {
        this.name = name;
        this.documents = new ArrayList<>();
        addDocument(d);
    }

    /**
     * Associa el Document d amb l'autor.
     * @param d Document d existent.
     */
    public void addDocument(Document d) {
        documents.add(d);
    }

    /**
     * Elimina el Document d de l'autor.
     * @param d Document d existent.
     */
    public void deleteDocument(Document d) {
        documents.remove(d);
    }

    /**
     * Busca els títols dels documents d'un autor.
     * @return Una ArrayList amb els títols dels seus documents.
     */
    public ArrayList<Document> getTitles() {
        ArrayList<Document> arr = new ArrayList<>(documents);
        arr.sort((d1, d2) -> {
            if (d1.getTitle().compareTo(d2.getTitle()) != 0)
                return d1.getTitle().compareTo(d2.getTitle());
            else return d1.getAuthor().compareTo(d2.getAuthor());
        });
        return arr;
    }

    /**
     * Retorna el nom de l'autor.
     * @return String amb el nom de l'autor.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retorna el número de documents que té un autor.
     * @return el número de documents que té un autor.
     */
    public Integer getNumberDocs() {
        return this.documents.size();
    }

}