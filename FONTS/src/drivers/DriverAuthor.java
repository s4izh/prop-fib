package drivers;

import domain.classes.Author;
import domain.classes.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DriverAuthor {
    private HashMap<String, Author> prova;

    private Scanner sc;

    // ------ funcio per crear contingut pels documents ------

    public void testAddAuthor() {
        System.out.println("Indica el nom de l'autor que vols afegir:");
        String authorName = sc.nextLine();
        if (!prova.containsKey(authorName)) {
            String content = "hey";

            Document doc = new Document("a", authorName, content);
            prova.put(authorName, new Author(authorName, doc));
            System.out.println("S'ha creat una nova instancia de la classe Author!");
        } else {
            System.out.println("Ja existeix l'autor indicat!");
        }
    }

    public void testDeleteAuthor() {
        System.out.println("Llista d'autors existents:");
        for (String keys : prova.keySet()) {
            System.out.println(keys);
        }

        System.out.println("Indica el nom de l'autor que vols eliminar:");
        String authorName = sc.nextLine();
        if (prova.containsKey(authorName)) {
            prova.remove(authorName);
            System.out.println("S'ha eliminat l'autor " + authorName + "!");
        } else {
            System.out.println("No existeix l'autor indicat!");
        }
    }

    public void listAuthors() {
        System.out.println("Llista d'autors existents:");
        for (String keys : prova.keySet()) {
            System.out.println(keys);
        }
    }

    public static void main(String[] args) {
        DriverAuthor d = new DriverAuthor();
        System.out.println("Estas provant el driver de la classe Author");
        d.sc = new Scanner(System.in);
        d.prova = new HashMap<>();
        String i = "";
        show_options();
        while (!i.equals("4")) {
            i = d.sc.nextLine();
            switch (i) {
                case "1":
                    d.testAddAuthor();
                    break;

                case "2":
                    d.testDeleteAuthor();
                    break;
                case "3":
                    d.listAuthors();
                    break;
                case "4":
                    System.out.println("Tancant el driver...");
                    d.sc.close();
                    break;

                default:
                    break;
            }
            if (!i.equals("4")) show_options();
        }
        d.sc.close();
    }

    private static void show_options() {
        System.out.println("(1|Afegir autor)");
        System.out.println("(2|Borrar autor)");
        System.out.println("(3|Llistar autors)");
        System.out.println("(4|Sortir)");
    }
}