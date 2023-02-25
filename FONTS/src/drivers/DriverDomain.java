package drivers;

import domain.controllers.DomainController;

import java.util.*;

public class DriverDomain {

    /**
     * Controlar del domini
     */
    private final DomainController dc;

    /**
     * Boolean que s'utilitza a l'hora de realitzar una consulta sobre una altra prèvia.
     */
    private Boolean firstQuery;

    /**
     * Scanner utilitzat pels inputs.
     */
    public Scanner in;

    /**
     * String utilitzat per l'estètica del sistema en consola
     */
    private static final String separator = ">----------------------------------------------------------<";

    /**
     * Inicialització del DriverDomain.
     */
    public DriverDomain() {
        dc = DomainController.getInstance();
        firstQuery = true;
        dc.iniDomainController();
    }

    /**
     * Funció per llegir el títol d'un document
     *
     * @return retorna un string amb el títol introduït per l'usuari
     */
    public String readTitle() {
        String title;
        do {
            System.out.println("Titol del document a crear: ");
            title = in.nextLine();
            if (title.length() == 0) System.out.println("El títol no pot ser buit");

        } while (title.length() == 0);
        return title;
    }

    /**
     * Funció per llegir el nom d'un autor
     *
     * @return retorna un string amb el nom d'un autor introduït per l'usuari
     */
    public String readName() {
        String name;
        do {
            System.out.println("Nom de l'autor: ");
            name = in.nextLine();
            if (name.length() == 0) System.out.println("El nom de l'autor no pot ser buit");

        } while (name.length() == 0);
        return name;
    }

    /**
     * Funció per llegir una expressió booleana.
     *
     * @return retorna una expressió booleana.
     */
    public String readBoolean() {
        String line;
        do {
            System.out.println("Inserta l'expressió booleana: ");
            line = in.nextLine();
            if (line.length() == 0) System.out.println("L'expressió booleana no pot ser buida");

        } while (line.length() == 0);
        return line;
    }

    /**
     * Funció per crear un nou document al sistema
     *
     * @throws Exception es llença si ja existeix un document amb el mateix títol i autor
     */
    public void testCrearDocument() throws Exception {
        String title = readTitle();
        String author = readName();

        String line;
        do {
            System.out.println("Introdueix el contingut:");
            line = in.nextLine();
            if (line.isEmpty())
                System.out.println("El contingut no pot ser buit");
        } while (line.isEmpty());

        dc.addDocument(title, author, line);
        System.out.println(separator);
        System.out.println("S'ha creat el document amb títol (" + title + ") i autor (" + author + ")");
    }

    /**
     * Funció per eliminar un document del sistema
     *
     * @throws Exception es llença si no existeix cap document identificat pel títol i l'autor indicats
     */
    public void testBorrarDocument() throws Exception {
        testLlistarDocuments();
        String title;
        do {
            System.out.println("Titol del document a esborrar: ");
            title = in.nextLine();
            if (title.length() == 0) System.out.println("El títol no pot ser buit");

        } while (title.length() == 0);
        String author = readName();
        dc.deleteDocument(title, author);
        System.out.println(separator);
        System.out.println("S'ha esborrat el document amb títol (" + title + ") i autor (" + author + ")");
    }


    /**
     * Funció per modificar el contingut d'un document
     *
     * @throws Exception es llença en cas que no existeixi el document identificat pel títol i l'autor
     */
    public void testModificarDocument() throws Exception {
        testLlistarDocuments();
        String oldTitle;
        do {
            System.out.println("Titol del document a modificar: ");
            oldTitle = in.nextLine();
            if (oldTitle.length() == 0) System.out.println("El títol no pot ser buit");

        } while (oldTitle.length() == 0);
        String oldAuthor = readName();

            String line;
            do {
                System.out.println("Introdueix el contingut:");
                line = in.nextLine();
                if (line.isEmpty())
                    System.out.println("El contingut no pot ser buit");
            } while (line.isEmpty());

            dc.modifyDocument(oldTitle, oldAuthor, oldTitle, oldAuthor, line);
            System.out.println(separator);
            System.out.println("S'ha modificat el document amb títol (" + oldTitle + ") i autor (" + oldAuthor + ").");
    }

    /**
     * Funció per llistar tots els documents existents al sistema sense cambiar el result
     */
    public void testLlistarDocuments() {
        ArrayList<String> out = dc.getAllDocumentsNames();
        System.out.println(separator);
        System.out.println("Llista de documents -- (títol - autor)");
        System.out.println("--------------------------------------");
        for (String s : out) {
            System.out.print(s + "\n");
        }
        System.out.println("--------------------------------------");
    }
    /**
     * Funció per llistar tots els documents existents al sistema cambiant el result
     */
    public void testLlistarDocuments2() {
        ArrayList<String> out = dc.getAllDocumentsNames();
        System.out.println(separator);
        System.out.println("Llista de documents -- (títol - autor)");
        System.out.println("--------------------------------------");
        for (String s : out) {
            System.out.print(s);
        }
        System.out.println("--------------------------------------");
    }
    /**
     * Funció per consulta el contingut d'un document
     *
     * @throws Exception es llença una excepció en cas que no existeixi el document identificat pel títol i l'autor
     */
    public void testConsultaContingutDocument() throws Exception {
        testLlistarDocuments();
        String title;
        do {
            System.out.println("Titol del document a consultar: ");
            title = in.nextLine();
            if (title.length() == 0) System.out.println("El títol no pot ser buit");
        } while (title.length() == 0);
        String name = readName();

        String content = dc.getContentWithTitleAuthor(title, name);
        System.out.println(separator);
        System.out.println("Contingut del document amb títol (" + title + ") i autor (" + name + "):\n>");
        System.out.println(content);
    }

    /**
     * Funció per consultar els títols d'un autor
     *
     * @throws Exception es llença una excepció si no existeix l'autor
     */
    public void testConsultaTitolsAutors() throws Exception {
        String name = readName();
        ArrayList<String> resultTitles = dc.getTitlesByAuthor(name);
        System.out.println(separator);
        System.out.println("(" + name + ") és autor dels seguents documents:");
        for (String title : resultTitles) {
            System.out.println(title + "\n");
        }
        firstQuery = false;
    }

    /**
     * Funció per consultar els documents que compleixin que el seu títol comença per un prefix
     */
    public void testConsultaTitolsDocumentsPrefix() {
        testLlistarDocuments();
        if (!firstQuery) {
            System.out.println("Vols aplicar la consulta sobre el resultat anterior? s/n ");
            String answer = in.nextLine();
            if (Objects.equals(answer, "n")) {
                firstQuery = true;
            } else if (!Objects.equals(answer, "s")) {
                System.out.println("Resposta mal formulada! (s o n)");
                return;
            }
        }
        System.out.println("Introdueix el prefix: ");
        String prefix = in.nextLine();
        System.out.println(separator);
        System.out.println("Titols dels documents amb prefix (" + prefix + "):");
        ArrayList<String> resultTitles = dc.getTitlesByPrefix(prefix, firstQuery);
        for (String tit : resultTitles) {
            System.out.println(tit);
        }
        firstQuery = false;
    }

    /**
     * Funció per consultar els k documents més semblants a un document
     *
     * @throws Exception es llença una excepció en cas que no existeixi el document identificat pel títol i autor
     */
    public void testConsultaDocumentsSemblants() throws Exception {
        testLlistarDocuments();
        System.out.println("Primer indica el document escollit");
        String title;
        do {
            System.out.println("Titol del document a consultar: ");
            title = in.nextLine();
            if (title.length() == 0) System.out.println("El títol no pot ser buit");
        } while (title.length() == 0);
        String name = readName();
        System.out.println("Quants documents semblants vols?");
        Integer k = Integer.parseInt(in.nextLine());
        ArrayList<String> out = dc.getSimilarDocuments(title, name, k);
        System.out.println(separator);
        System.out.println("Els " + k + " documents més semblants a (" + title + ", " + name + ") en ordre són:");
        for (String s : out) {
            System.out.println(s + "\n");
        }
        firstQuery = false;
    }

    /**
     * Funció per consultar amb expressions booleanes
     *
     * @throws Exception es llença una excepció interna a la classe BooleanExpression
     */
    public void testConsultaExpressionsBooleanes() throws Exception {
        testLlistarDocuments();
        if (!firstQuery) {
            System.out.println("Vols aplicar la consulta sobre el resultat anterior? (s/n) ");
            String answer = in.nextLine();
            if (Objects.equals(answer, "n")) {
                firstQuery = true;
            } else if (!Objects.equals(answer, "s")) {
                System.out.println("Resposta mal formulada! (s/n)");
                return;
            }
        }
        String exp = readBoolean();
        ArrayList<String> out = dc.getTitlesByBoolean(exp, firstQuery);

        for (String s : out) {
            System.out.println(s + "\n");
        }
        firstQuery = false;
    }

    /**
     * Funció per eliminar una expressió booleana
     */
    public void testEliminaExpressioBooleana() throws Exception {
        ArrayList<String> expBooleanes = dc.getBooleanExpressions();
        System.out.println("Expressions booleanes registrades: ");
        for (String word : expBooleanes) {
            System.out.println(word);
        }
        String exp = readBoolean();
        dc.deleteBooleanExpression(exp);
        System.out.println("S'ha eliminat l'expressió: " + exp);
    }


    /**
     * Funció per modificar una expressió booleana de les ja existents al sistema
     *
     * @throws Exception es llença una excepció interna a la classe BooleanExpression
     */
    public void testModificaExpressioBooleana() throws Exception {
        ArrayList<String> expBooleanes = dc.getBooleanExpressions();
        System.out.println("Expressions booleanes registrades: ");
        for (String word : expBooleanes) {
            System.out.println(word);
        }
        String exp = readBoolean();
        System.out.println("Introdueix l'expressió modificada: ");
        String exp2 = readBoolean();
        dc.modifyBooleanExpression(exp, exp2);
    }


    /**
     * Funció per consultar els autors que cumpleixen que el seu nom comença per un prefix
     */
    public void testConsultaAutorsPrefix() {
        System.out.println("Introdueix el prefix de l'autor: ");
        String pref = in.nextLine();
        System.out.println("Resultats ordenats ascendentment (0) o descendentment (1). (Escriu 0 o 1)");
        String order = in.nextLine();

        if ((order.equals("0")) || (order.equals("1"))) {
            ArrayList<String> out = dc.getAuthorsByPrefix(pref, order);
            for (String aut : out) {
                System.out.println(aut + "\n");
            }
        } else {
            System.out.println("Resposta mal formulada! (0 o 1)");
        }
    }

    public static void main(String[] args) throws Exception {
        DriverDomain dd = new DriverDomain();

        System.out.println("Estas provant el drivel del Domini:");
        show_options();
        dd.in = new Scanner(System.in);
        String input = "";
        while (!input.equals("0") && !input.equals("sortir")) {
            input = dd.in.nextLine();
            switch (input) {
                case "0":
                    System.out.println("Tancant el driver...");
                    dd.in.close();
                    break;
                case "1":
                case "Crear document": {
                    dd.testCrearDocument();
                    break;
                }
                case "2":
                case "Borrar document": {
                    dd.testBorrarDocument();
                    break;
                }
                case "3":
                case "Modificar document": {
                    dd.testModificarDocument();
                    break;
                }
                case "4":
                case "Llistar tots els documents": {
                    dd.testLlistarDocuments2();
                    break;
                }
                case "5":
                case "Consulta contingut d'un document": { //DocumentContent
                    dd.testConsultaContingutDocument();
                    break;
                }
                case "6":
                case "Consulta títols d'un autor": { //TitlesByAuthor
                    dd.testConsultaTitolsAutors();
                    break;
                }
                case "7":
                case "Consulta títols de documents per prefix": { //P
                    dd.testConsultaTitolsDocumentsPrefix();
                    break;
                }
                case "8":
                case "Obté documents semblants": { //SimilarDocuments
                    dd.testConsultaDocumentsSemblants();
                    break;
                }
                case "9":
                case "Consulta amb expressió booleana": { //BooleanExp
                    dd.testConsultaExpressionsBooleanes();
                    break;
                }
                case "10":
                case "Elimina expressió booleana": {
                    dd.testEliminaExpressioBooleana();
                    break;
                }
                case "11":
                case "Modifica expressió booleana": {
                    dd.testModificaExpressioBooleana();
                    break;
                }
                case "12":
                case "Consulta autors per prefix": { //Prefix
                    dd.testConsultaAutorsPrefix();
                    break;
                }
                default:
                    break;
            }
            if (!input.equals("0")) show_options();
        }
        dd.in.close();
    }

    /**
     * Funció per mostrar en la consola les operacions possibles a duur a terme en el sistema
     */
    private static void show_options() {
        System.out.println(separator);
        System.out.println("  1 | Crear document");
        System.out.println("  2 | Eliminar document");
        System.out.println("  3 | Modificar document");
        System.out.println("  4 | Llistar tots els documents");
        System.out.println("  5 | Consultar contingut d'un document");
        System.out.println("  6 | Consultar títols d'un autor");
        System.out.println("  7 | Consultar títols de documents per prefix");
        System.out.println("  8 | Obtenir documents semblants");
        System.out.println("  9 | Consultar amb expressió booleana");
        System.out.println(" 10 | Eliminar expressió booleana");
        System.out.println(" 11 | Modificar expressió booleana");
        System.out.println(" 12 | Consultar autors per prefix");
        System.out.println("  0 | Sortir");
        System.out.println(separator);
    }
}