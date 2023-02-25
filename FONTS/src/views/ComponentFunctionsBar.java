package views;

import presentation.PresentationController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ComponentFunctionsBar extends JToolBar {
    /**
     * Instància del PresentationController.
     */
    PresentationController presentationController = PresentationController.getInstance();

    /**
     * JButton per crear un nou document.
     */
    final JButton bCreateDoc = new JButton("Nou", createImageIcon("../icon/newDoc.png"));

    /**
     * JButton utilitzat per visualitzar el contingut d'un document.
     */
    final JButton bVisualizeDoc = new JButton("Obrir", createImageIcon("../icon/visualizeDoc.png"));

    /**
     * JButton que permet l'edició del contingut d'un document del gestor
     */
    final JButton bEditContent = new JButton("Editar", createImageIcon("../icon/editDoc.png"));

    /**
     * JButton utilitzat pero eliminar un document.
     */
    final JButton bRemoveDoc = new JButton("Eliminar", createImageIcon("../icon/removeDoc.png"));

    /**
     * JButton utilitzat per carregar un arxiu local de tipus txt, json o xml al sistema.
     */
    final JButton bLoadFile = new JButton("Carregar", createImageIcon("../icon/loadDoc.png"));

    /**
     * JButton que permet tancar l'aplicació.
     */
    final JButton bExit = new JButton("Sortir", createImageIcon("../icon/exit.png"));

    /**
     * JButton utilitzat per descarregar un document del sistema.
     */
    final JButton bDownloadDoc = new JButton("Descarregar", createImageIcon("../icon/downloadDoc.png"));

    /**
     * String array utilitzat en el JComboBox per mostrar les consultes que es poden realitzar.
     */
    private final String[] optionsToChooseQueries = {"Obtenir documents similars", "Consulta booleana"};

    /**
     * JComboBox mitjançant el qual es selecciona una consulta a realitzar.
     */
    final JComboBox<String> jQueries = new JComboBox<>(optionsToChooseQueries);

    /**
     * JFrame emprat per la creació del pop up per crear un document.
     */
    JFrame frame;
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ViewMenu.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    ComponentFunctionsBar(){
        setBorder(new EmptyBorder(10,0,5,0));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        jQueries.setRenderer(new ComponentComboBox("Escull consulta"));
        jQueries.setSelectedIndex(-1);

        Dimension dimCombo = bCreateDoc.getPreferredSize();
        dimCombo.width = 150;
        jQueries.setMaximumSize(dimCombo);

        addSeparator();
        add(bCreateDoc);
        addSeparator();
        add(bEditContent);
        addSeparator();
        add(bRemoveDoc);
        addSeparator();

        add(bLoadFile);
        addSeparator();

        add(bDownloadDoc);
        addSeparator();
        add(bVisualizeDoc);
        addSeparator();
        add(bExit);
        addSeparator();
        add(Box.createGlue());
        add(jQueries);

        setFloatable(false);
        addSeparator();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        JDialog popUpCreateDoc =  new JDialog(frame, "Crear document");
        popUpCreateDoc.setSize(500, 300);
        popUpCreateDoc.setLayout(null);
        popUpCreateDoc.setLocation(dim.width/2-250, dim.height/2-150);


        ActionListener lcreateDocument = e -> {

            JLabel txtTitle = new JLabel("Títol del document:");
            txtTitle.setBounds(50, 20, 400, 30);

            JLabel txtAuthor = new JLabel("Autor del document:");
            txtAuthor.setBounds(50, 100, 400, 30);

            JTextArea areaTitle = new JTextArea();
            areaTitle.setBounds(50, 60, 300, 20);
            JTextArea areaAuthor = new JTextArea();
            areaAuthor.setBounds(50, 140, 300, 20);
            JButton bContinueCreation = new JButton("Continuar");
            JButton bCancelCreation = new JButton("Cancel·lar");

            areaTitle.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    boolean b = ke.getKeyChar() == '\n';
                    if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                        areaTitle.setEditable(true);
                    }
                    else areaTitle.setEditable(!b);
                }
            });

            areaAuthor.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    boolean b = ke.getKeyChar() == '\n';
                    if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                        areaAuthor.setEditable(true);
                    }
                    else areaAuthor.setEditable(!b);
                }
            });

            bContinueCreation.setBounds(50, 190, 150, 30);

            bCancelCreation.setBounds(300, 190, 150, 30);
            popUpCreateDoc.add(txtTitle);
            popUpCreateDoc.add(areaTitle);
            popUpCreateDoc.add(txtAuthor);
            popUpCreateDoc.add(areaAuthor);
            popUpCreateDoc.add(bCancelCreation);
            popUpCreateDoc.add(bContinueCreation);
            popUpCreateDoc.setResizable(false);
            popUpCreateDoc.repaint();
            popUpCreateDoc.setVisible(true);

            ActionListener lCancelCreation = e1 -> {
                popUpCreateDoc.dispose();
                popUpCreateDoc.setVisible(false);
                presentationController.visibleViewMenu();
            };

            ActionListener lContinueCreation = e12 -> {
                String titleNew = areaTitle.getText();
                String authorNew = areaAuthor.getText();
                areaTitle.setText("");
                areaAuthor.setText("");
                if((titleNew.isBlank() || authorNew.isBlank())){
                    JOptionPane.showMessageDialog(null, "El títol i l'autor no poden ser buits");
                    presentationController.visibleViewMenu();
                }
                else if(presentationController.existsDocument(titleNew, authorNew)){
                    JOptionPane.showMessageDialog(null, "Ja existeix un document amb el mateix títol i autor");
                    presentationController.visibleViewMenu();
                }
                else{
                    try {
                        presentationController.viewEditDocument(titleNew, authorNew, true);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
                popUpCreateDoc.dispose();
                popUpCreateDoc.setVisible(false);
            };

            bContinueCreation.addActionListener(lContinueCreation);
            bCancelCreation.addActionListener(lCancelCreation);
        };

        ActionListener lexit = e -> {
            presentationController.manageDummyMetadata();
            System.exit(0);
        };

        bCreateDoc.addActionListener(lcreateDocument);
        bExit.addActionListener(lexit);
    }
}
