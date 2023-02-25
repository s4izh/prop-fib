package views;

import presentation.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class ViewVisualizeDocument extends JFrame {

    /**
     * Instància del PresentationController
     */
    private static final PresentationController presentationController = PresentationController.getInstance();

    /**
     * Camp per mostrar el títol del document.
     */
    private JLabel txtName;
    /**
     * Camp per mostrar l'autor del document.
     */
    private JLabel txtAuthor;

    /**
     * Camp per mostrar el contingut del document.
     */
    private JTextArea areaContent;


    /**
     * Assigna els nous valors del document a visualitzar.
     * @param title títol del document.
     * @param author autor del document.
     * @throws Exception es llença una excepció si no existeix el document.
     */
    public void setValues(String title, String author) throws Exception {

        txtName.setText("Títol: " + title);
        txtAuthor.setText("Autor: " + author);
        areaContent.setText(presentationController.getContent(title, author));

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setSize(700,600);
        Dimension dim2 = Toolkit.getDefaultToolkit().getScreenSize();
        dim2.width = 350;
        dim2.height = 250;
        setMinimumSize(dim2);
        repaint();

    }

    /**
     * Constructora de la vista per visualitzar el contingut d'un document.
     * @param title títol del document.
     * @param author autor del document.
     * @throws Exception si no existeix el document identificat pel títol i l'autor.
     */
    public ViewVisualizeDocument(String title, String author) throws Exception {


        JToolBar toolIdDoc = new JToolBar();
        toolIdDoc.setBorder(new EmptyBorder(10,0,10,5));
        JToolBar toolFunction = new JToolBar();
        toolFunction.setBorder(new EmptyBorder(10,5,5,5));

        JPanel panelVisualizeDocument = new JPanel();
        panelVisualizeDocument.setBorder(new EmptyBorder(10,25,10,25));
        toolIdDoc.setLayout(new BoxLayout(toolIdDoc, BoxLayout.Y_AXIS));
        toolFunction.setLayout(new BoxLayout(toolFunction, BoxLayout.X_AXIS));

        panelVisualizeDocument.setLayout(new BoxLayout(panelVisualizeDocument, BoxLayout.Y_AXIS));

        setResizable(true);
        setTitle("GESTOR DE DOCUMENTS");

        toolIdDoc.setOpaque(false);
        toolIdDoc.setBorderPainted(false);
        toolIdDoc.setFloatable(false);
        toolFunction.setFloatable(false);

        txtName = new JLabel("Títol: " + title);
        txtAuthor = new JLabel("Autor: " + author);

        areaContent = new JTextArea(presentationController.getContent(title, author));
        areaContent.setEditable(false);

        areaContent.setLineWrap(true);
        areaContent.setWrapStyleWord(true);

        JScrollPane scrollContent = new JScrollPane(areaContent);
        txtName.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtAuthor.setAlignmentX(Component.LEFT_ALIGNMENT);
        toolIdDoc.add(txtName);
        toolIdDoc.add(Box.createHorizontalBox());
        toolIdDoc.addSeparator();
        toolIdDoc.add(txtAuthor);
        toolIdDoc.add(Box.createHorizontalBox());
        JLabel txtContent = new JLabel("Contingut:");
        txtContent.setAlignmentX(Component.LEFT_ALIGNMENT);
        toolIdDoc.addSeparator();
        toolIdDoc.add(scrollContent);
        toolIdDoc.add(Box.createHorizontalBox());

        toolFunction.add(Box.createGlue());
        JButton bExit = new JButton("Enrere");
        toolFunction.add(bExit);
        toolFunction.add(Box.createGlue());

        panelVisualizeDocument.add(scrollContent);
        add(toolIdDoc, BorderLayout.PAGE_START);
        add(panelVisualizeDocument, BorderLayout.CENTER);
        add(toolFunction, BorderLayout.PAGE_END);
        setSize(700, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        Dimension dim2 = Toolkit.getDefaultToolkit().getScreenSize();
        dim2.width = 350;
        dim2.height = 250;
        setMinimumSize(dim2);

        ActionListener lExit = e -> {
            setVisible(false);
            presentationController.visibleViewMenu();
        };

        bExit.addActionListener(lExit);

    }
}
