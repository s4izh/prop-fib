package views;

import presentation.PresentationController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ComponentSearchBar extends JToolBar {

    /**
     * Instància del PresentationController.
     */
    private final PresentationController presentationController = PresentationController.getInstance();

    /**
     * Cercador per realitzar la consulta de títols per prefix.
     */
    JTextField prefixTitles = new ComponentPrefixFilters(new JTextField(), "icon",    "Cerca per títol...");
    /**
     * Cercador per realitzar la consulta d'autors per prefix.
     */
    JTextField prefixAuthors = new ComponentPrefixFilters(new JTextField(), "autor",    "Cerca per autor...");

    /**
     * Desplegable per realitzar la consulta de títols d'un autor.
     */
    public JComboBox<String> jAuthors = new JComboBox<>();

    /**
     * Funció per actualitzar el JComboBox que mostrar els autors.
     */
    public void updateAuthors(){
        //jAuthors = new JComboBox<>();
        DefaultComboBoxModel modelCombo = new DefaultComboBoxModel();
        ArrayList<String> authors = new ArrayList<>();
        authors = presentationController.getAuthorsByPrefix("", "0");
        for(String newAuthor : authors){
            modelCombo.addElement(newAuthor);
        }
        jAuthors.setModel(modelCombo);
        jAuthors.setRenderer(new ComponentComboBox("Títols d'un autor"));
        jAuthors.setSelectedIndex(-1);
    }

    /**
     * Constructora del component de la barra de cerques i la consulta de títols d'un autor.
     */
    ComponentSearchBar(){
        setLayout(new GridLayout(1,3));
        updateAuthors();

        Dimension dT = new Dimension();
        dT.width = 100;
        dT.height = 20;
        add(prefixTitles, BorderLayout.LINE_START);
        addSeparator();
        add(jAuthors, BorderLayout.CENTER);
        addSeparator();
        add(prefixAuthors, BorderLayout.LINE_END);

        dT.width = 400;
        prefixTitles.setMinimumSize(dT);
        prefixAuthors.setMinimumSize(dT);
        prefixTitles.setMaximumSize(dT);
        prefixAuthors.setMaximumSize(dT);

        jAuthors.setSize(jAuthors.getPreferredSize());

        dT.width = 10000;
        setMaximumSize(dT);
        setOpaque(false);
        setBorderPainted(false);
        setFloatable(false);
    }
}
