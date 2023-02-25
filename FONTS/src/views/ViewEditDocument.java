package views;

import presentation.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class ViewEditDocument extends JFrame {

    /**
     * Instància del PresentationController.
     */
    private static final PresentationController presentationController = PresentationController.getInstance();

    /**
     * Títol del document a editar.
     */
    private String title;

    /**
     * Autor del document a editar.
     */
    private String author;

    /**
     * Booleà per indicar si el document és nou o s'està modificant.
     */
    private Boolean create;

    /**
     * Camp per introduir el nou títol si s'està modificant el document.
     */
    private JTextArea txtTitle;

    /**
     * Camp per introduir el nou autor si s'està modificant el document.
     */
    private JTextArea txtAuthor;
    /**
     * On s'escriu el contingut.
     */
    private JTextArea areaContent;

    /**
     * Funció per reassignar els nous valors de la vista.
     * @param title títol del document.
     * @param author autor del document.
     * @param create booleà per indicar si s'està creant o modificant.
     * @throws Exception es llença una excepció si s'està modificant i el títol i l'autor escollits ja identifiquen a un altre document.
     */
    public void setValues(String title, String author, boolean create) throws Exception {
        txtTitle.setText("");
        txtAuthor.setText("");
        this.title = title;
        this.author = author;
        this.create = create;
        txtTitle.setText(title);
        txtAuthor.setText(author);
        areaContent.setText("");
        if(create){
            areaContent.setText("");
            txtTitle.setEditable(false);
            txtAuthor.setEditable(false);
        }
        else{
            areaContent.setText(presentationController.getContent(title, author));
            txtTitle.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    txtTitle.getText();
                    boolean b = ke.getKeyChar() == '\n';
                    if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                        txtTitle.setEditable(true);
                    }
                    else txtTitle.setEditable(!b);
                }
            });

            txtAuthor.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    txtAuthor.getText();
                    boolean b = ke.getKeyChar() == '\n';
                    if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                        txtAuthor.setEditable(true);
                    }
                    else txtAuthor.setEditable(!b);
                }
            });
        }

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
     * Constructora de la vista que permet crear el contingut d'un document si es vol crear un document, o modificar el seu títol, autor i contingut si ja existia.
     * @param title títol del document.
     * @param author autor del document.
     * @param create booleà per indicar si es vol crear o modificar.
     * @throws Exception es llença una excepció si es modifica el nom d'un document pel d'un ja existent
     */
    public ViewEditDocument(String title, String author, boolean create) throws Exception {
        JToolBar toolIdDoc = new JToolBar();
        toolIdDoc.setBorder(new EmptyBorder(10,0,10,5));
        JToolBar toolFunction = new JToolBar();
        toolFunction.setBorder(new EmptyBorder(10,5,5,5));

        JPanel panelEditDocument = new JPanel();
        panelEditDocument.setBorder(new EmptyBorder(10,10,10,10));
        toolIdDoc.setLayout(new BoxLayout(toolIdDoc, BoxLayout.Y_AXIS));
        toolFunction.setLayout(new BoxLayout(toolFunction, BoxLayout.X_AXIS));

        panelEditDocument.setLayout(new BoxLayout(panelEditDocument, BoxLayout.Y_AXIS));

        setResizable(true);
        setTitle("GESTOR DE DOCUMENTS");

        toolIdDoc.setOpaque(false);
        toolIdDoc.setBorderPainted(false);
        toolIdDoc.setFloatable(false);
        toolFunction.setFloatable(false);
        this.title = title;
        this.author = author;
        this.create = create;
        JLabel titleLabel = new JLabel("Títol");
        JLabel authorLabel = new JLabel("Autor");
        txtTitle = new JTextArea(title);
        txtAuthor = new JTextArea(author);
        Dimension d = titleLabel.getPreferredSize();
        d.width = 200;
        txtTitle.setMinimumSize(d);
        txtAuthor.setMinimumSize(d);
        d.width = 250;
        txtTitle.setMaximumSize(d);
        txtAuthor.setMaximumSize(d);

        if(create){
            areaContent = new JTextArea();
            txtTitle.setEditable(false);
            txtAuthor.setEditable(false);
        }
        else{
            areaContent = new JTextArea(presentationController.getContent(title, author));
            txtTitle.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    txtTitle.getText();
                    boolean b = ke.getKeyChar() == '\n';
                    if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                        txtTitle.setEditable(true);
                    }
                    else txtTitle.setEditable(!b);
                }
            });

            txtAuthor.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    txtAuthor.getText();
                    boolean b = ke.getKeyChar() == '\n';
                    if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                        txtAuthor.setEditable(true);
                    }
                    else txtAuthor.setEditable(!b);
                }
            });
        }
        areaContent.setLineWrap(true);
        areaContent.setWrapStyleWord(true);

        JScrollPane scrollContent = new JScrollPane(areaContent);

        txtTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtAuthor.setAlignmentX(Component.LEFT_ALIGNMENT);
        toolIdDoc.add(titleLabel);
        toolIdDoc.addSeparator();
        toolIdDoc.add(txtTitle);
        toolIdDoc.add(Box.createHorizontalBox());
        toolIdDoc.addSeparator();
        toolIdDoc.add(authorLabel);
        toolIdDoc.addSeparator();
        toolIdDoc.add(txtAuthor);
        toolIdDoc.add(Box.createHorizontalBox());
        JLabel txtContent = new JLabel("Contingut:");
        txtContent.setAlignmentX(Component.LEFT_ALIGNMENT);
        toolIdDoc.addSeparator();
        toolIdDoc.add(scrollContent);
        toolIdDoc.add(Box.createHorizontalBox());

        toolFunction.add(Box.createGlue());
        JButton bEdit = new JButton("Guardar");
        toolFunction.add(bEdit);
        toolFunction.addSeparator();
        JButton bExit = new JButton("Cancel·lar");
        toolFunction.add(bExit);
        toolFunction.add(Box.createGlue());

        panelEditDocument.add(scrollContent);
        add(toolIdDoc, BorderLayout.PAGE_START);
        add(panelEditDocument, BorderLayout.CENTER);
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

        ActionListener lEdit = e -> {
            String newTitle = txtTitle.getText();
            String newAuthor = txtAuthor.getText();
            String content = areaContent.getText();
            if(newTitle.isBlank() || newAuthor.isBlank()){
                if(newTitle.isBlank()){
                    JOptionPane.showMessageDialog(null,"El títol no pot ser buit");
                    presentationController.visibleViewMenu();
                }
                else{
                    JOptionPane.showMessageDialog(null,"L'autor no pot ser buit");
                    presentationController.visibleViewMenu();
                }
            }
            else if(content.isBlank()){
                JOptionPane.showMessageDialog(null,"El contingut no pot ser buit");
                presentationController.visibleViewMenu();
            }
            else{
                try {
                    if (!this.create) presentationController.modifyDocument(this.title,this.author,newTitle,newAuthor,content);
                    else presentationController.createDocument(this.title, this.author, content);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    presentationController.visibleViewMenu();
                }
            }
            setVisible(false);

        };

        ActionListener lExit = e -> {
            setVisible(false);
            presentationController.visibleViewMenu();
        };

        bEdit.addActionListener(lEdit);
        bExit.addActionListener(lExit);
    }
}

