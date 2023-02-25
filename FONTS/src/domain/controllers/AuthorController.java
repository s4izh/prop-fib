package domain.controllers;

import domain.classes.Author;
import domain.classes.Document;
import java.util.ArrayList;
import java.util.Comparator;

public class AuthorController {
    /**
     * Arraylist per guardar els autors amb clau <nom,autor>
     */
    private final ArrayList<Author> authors;

    /**
     * Crea un AuthorController.
     */
    public AuthorController() {
        this.authors = new ArrayList<>();
    }

    /**
     * Crea un autor i l'associa amb un document.
     * @param name Nom de l'autor vàlid.
     * @param d Document d existent.
     */
    public void addAuthor(String name, Document d) {
        Author au = new Author(name, d);
        authors.add(au);
        authors.sort(Comparator.comparing(Author::getName));
    }

    /**
     * Afegeix un document a un autor existent.
     * @param name Nom de l'autor vàlid.
     * @param d    Document d existent.
     */
    public void addDocument(String name, Document d) {
        Author au = authors.get(find(name));
        au.addDocument(d);
    }

    /**
     * Troba la posició d'un autor a l'ArrayList.
     * @param name Nom de l'autor vàlid.
     */
    public Integer find(String name) {
        int l = 0;
        int r = authors.size() - 1;
        while (l <= r) {
            int mid = (r - l) / 2 + l;
            if (authors.get(mid).getName().equals(name)) {
                return mid;
            } else if (authors.get(mid).getName().compareTo(name) > 0) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }

    /**
     * Elimina un document a un autor existent.
     * @param name Nom de l'autor vàlid.
     * @param d    Document d existent.
     */
    public void deleteDocument(String name, Document d) {
        Author au = authors.get(find(name));
        au.deleteDocument(d);
        if (au.getNumberDocs() == 0) authors.remove(au);
    }

    /**
     * Obté un autor.
     * @param name Nom de l'autor vàlid.
     * @return Author amb nom name.
     */
    public Author getAuthor(String name) {
        return authors.get(find(name));
    }

    /**
     * Obté els títols dels documents d'un autor.
     * @param name Nom de l'autor vàlid.
     * @return Un ArrayList amb els títols dels seus documents.
     */
    public ArrayList<Document> getTitles(String name) {
        Author au = authors.get(find(name));
        return au.getTitles();
    }

    /**
     * Obté els autors que contenen un prefix.
     * @param exp Prefix vàlid.
     * @return Un ArrayList amb els noms dels autors que contenen el prefix.
     */
    public ArrayList<String> getAuthors(String exp) {
        ArrayList<String> authorsRes = new ArrayList<>();

        int l = 0;
        int r = authors.size() - 1;
        int startIndex = -1;
        while (l <= r) {
            int mid = (r - l) / 2 + l;
            if (authors.get(mid).getName().startsWith(exp)) {
                startIndex = mid;
                r = mid - 1;
            } else if (authors.get(mid).getName().compareTo(exp) > 0) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }

        }
        if (startIndex != -1) {
            while (startIndex < authors.size() && authors.get(startIndex).getName().startsWith(exp)) {
                authorsRes.add(authors.get(startIndex).getName());
                ++startIndex;
            }
        }
        return authorsRes;
    }

    /**
     * Per saber si un autor existeix o no.
     * @param name Nom de l'autor vàlid.
     * @return Un boolean amb valor true si existeix l'autor o false si no.
     */
    public boolean authorExists(String name) {
        return (find(name) >= 0);
    }

    /**
     * Obté el número d'autor del sistema.
     * @return Un Integer amb el nombre d'autors del sistema.
     */
    public Integer size() {
        return authors.size();
    }
}