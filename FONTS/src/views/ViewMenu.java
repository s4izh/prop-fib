package views;

import presentation.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class ViewMenu extends JFrame {
    /**
     * Instància del PresentationController.
     */
    private static final PresentationController presentationController = PresentationController.getInstance();

    /**
     * ComponentFunctionsBar que implementa una JToolBar per crear la barra superior de la vista amb els botons per interactuar amb els documents.
     */
    private final ComponentFunctionsBar toolBar = new ComponentFunctionsBar();

    /**
     * Component que implementa una barra de cerca.
     */
    private final ComponentSearchBar toolBarSearch = new ComponentSearchBar();

    /**
     * Panel que permet fer scroll.
     */
    private final JScrollPane scrollPaneTable;

    private String newExpression;

    /**
     * Taula on es veuen els documents.
     */
    private JTable tableDocuments;

    /**
     * Títol del document seleccionat.
     */
    private String title = "";

    /**
     * Autor del document seleccionat.
     */
    private String author = "";

    /**
     * Funció per actualitzar la taula que mostra els documents a la vista.
     * @param docs documents que s'han de mostrar a la taula.
     */
    public void listTable(ArrayList<String> docs){

        title = "";
        author = "";
        DefaultTableModel model;
        String[] columnNames = { "Títol", "Autor" };
        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if(docs.size() == 0){
            title = "No s'han trobat resultats";
            author = "";
            Object[] aux = { title, author };
            model.addRow(aux);
        }
        else{
            for (String word : docs) {
                String[] cont = word.split(">");
                String title = cont[0];
                String author = cont[1];
                Object[] aux = { title, author };
                model.addRow(aux);

            }
        }
        tableDocuments = new JTable(model);
        tableDocuments.setBorder(new BevelBorder(BevelBorder.RAISED, null,null,null,null));
        tableDocuments.setOpaque(false);
        tableDocuments.setAutoCreateRowSorter(true);
        tableDocuments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPaneTable.setViewportView(tableDocuments);
        activeSelection();
        toolBarSearch.updateAuthors();
        tableDocuments.clearSelection();
    }

    /**
     * Funció per habilitar la selecció de files en la taula.
     */
    public void activeSelection(){

        clearSelection();

        tableDocuments.getSelectionModel().addListSelectionListener(event -> {
            title = tableDocuments.getValueAt(tableDocuments.getSelectedRow(), 0).toString();
            author = tableDocuments.getValueAt(tableDocuments.getSelectedRow(), 1).toString();
            if(Objects.equals(title, "No s'han trobat resultats")) title = "";
            if(Objects.equals(author, "Proba de nou")) author = "";
            if(!author.isEmpty()) author = author.replace("\r\n", "").replace("\n", "").replaceAll("\r", "");
        });

    }

    /**
     * Funció per desseleccionar la fila de la taula
     */
    public void clearSelection(){
        tableDocuments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if(e.getClickCount() == 2){
                    tableDocuments.getSelectionModel().addListSelectionListener(event -> {
                        title = tableDocuments.getValueAt(tableDocuments.getSelectedRow(), 0).toString();
                        author = tableDocuments.getValueAt(tableDocuments.getSelectedRow(), 1).toString();
                        if(Objects.equals(title, "No s'han trobat resultats")) title = "";
                        if(Objects.equals(author, "Proba de nou")) author = "";
                        if(!author.isEmpty()) author = author.replace("\r\n", "").replace("\n", "").replaceAll("\r", "");
                    });
                    try {
                        if(!title.isBlank() && !author.isBlank()) presentationController.viewVisualizeDocument(title, author);
                        listTable(presentationController.getResultDocuments());
                        setVisible(false);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });

    }

    /**
     * Constructora de la vista menú.
     */
    public ViewMenu() {
        JPanel panelMenu = new JPanel();
        panelMenu.setBorder(new EmptyBorder(5,10,10,10));
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));

        setResizable(true);
        setTitle("GESTOR DE DOCUMENTS");
        scrollPaneTable = new JScrollPane();

        listTable(presentationController.getAllDocuments());

        panelMenu.add(toolBarSearch);

        scrollPaneTable.setSize(670, 450);
        tableDocuments.setAutoCreateRowSorter(true);
        panelMenu.add(scrollPaneTable);

        pack();

        ComponentClockBar toolBarClock = new ComponentClockBar();
        panelMenu.add(toolBarClock);

        add(toolBar, BorderLayout.PAGE_START);
        add(panelMenu, BorderLayout.CENTER);

        Dimension dimMinimumSize = new Dimension();
        dimMinimumSize.width = 950;
        dimMinimumSize.height = 300;
        setMinimumSize(dimMinimumSize);
        setSize(1200, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Integer x = dim.width/2-this.getSize().width/2;
        Integer y = dim.height/2-this.getSize().height/2;
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JFrame frame = new JFrame();
        JDialog popUpQueries =  new JDialog(frame, "GESTOR DE DOCUMENTS");
        popUpQueries.setSize(500, 200);
        popUpQueries.setLayout(null);
        popUpQueries.setLocation(dim.width/2-250, dim.height/2-100);

        JDialog popUpSelectDoc =  new JDialog(frame, "GESTOR DE DOCUMENTS");
        popUpSelectDoc.setBounds(800, 300, 400, 200);
        popUpSelectDoc.setLayout(null);
        popUpSelectDoc.setLocation(dim.width/2-200, dim.height/2-100);

        JDialog popUpSimilar =  new JDialog(frame, "GESTOR DE DOCUMENTS");
        popUpSimilar.setBounds(800, 300, 400, 200);
        popUpSimilar.setLayout(null);
        popUpSimilar.setLocation(dim.width/2-200, dim.height/2-100);

        JDialog popUpRemove =  new JDialog(frame, "GESTOR DE DOCUMENTS");
        popUpRemove.setSize(500, 200);
        popUpRemove.setLayout(null);
        popUpRemove.setLocation(dim.width/2-250, dim.height/2-100);

        ActionListener leditDocument = e -> {
            if(!title.isEmpty() && !author.isEmpty()){
                try {
                    presentationController.viewEditDocument(title, author, false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                setVisible(false);
                title = "";
                author = "";
            }
            else{
                JOptionPane.showMessageDialog(null, "S'ha d'especificar el document a editar");
            }
            listTable(presentationController.getAllDocuments());
        };

        ActionListener lvisualizeDocument = e -> {
            if(!title.isEmpty() && !author.isEmpty()){
                try {
                    presentationController.viewVisualizeDocument(title, author);
                    listTable(presentationController.getResultDocuments());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                setVisible(false);
            }
            else{
                JOptionPane.showMessageDialog(null, "S'ha d'especificar el document a visualitzar");
            }
        };

        JDialog popUpDowndloadDoc =  new JDialog(frame, "Descarregar document");
        popUpDowndloadDoc.setBounds(800, 300, 500, 300);
        popUpDowndloadDoc.setLayout(null);
        popUpDowndloadDoc.setLocation(dim.width/2-250, dim.height/2-150);

        ActionListener ldownloadDocument = e -> {
            if(!title.isEmpty() && !author.isEmpty()){
                UIManager.put("FileChooser.openButtonText", "Obrir");
                UIManager.put("FileChooser.cancelButtonText", "Cancel·lar");
                JLabel txtFailDowndloadDocument = new JLabel("Escull el format: ");
                txtFailDowndloadDocument.setBounds(20, 10, 400, 50);
                String[] optionsToChooseExt = {".txt", ".xml", ".json"};
                JComboBox<String> extensionChooser = new JComboBox<>(optionsToChooseExt);
                JButton bFailDownloadDocument = new JButton("Cancel·lar");
                JButton bDowndloadFile = new JButton("Continuar");
                extensionChooser.setBounds(20, 60, 100, 30);
                bDowndloadFile.setBounds(200, 130, 150, 30);
                bFailDownloadDocument.setBounds(200, 180, 150, 30);

                popUpDowndloadDoc.add(bDowndloadFile);
                popUpDowndloadDoc.add(extensionChooser);
                popUpDowndloadDoc.add(txtFailDowndloadDocument);
                popUpDowndloadDoc.add(bFailDownloadDocument);
                popUpDowndloadDoc.setResizable(false);
                popUpDowndloadDoc.setVisible(true);
                ActionListener lAcceptExtension = e1 -> {
                    String fileExtension = (String) extensionChooser.getSelectedItem();
                    popUpDowndloadDoc.dispose();
                    popUpDowndloadDoc.setVisible(false);
                    presentationController.viewDownloadFile(title, author, fileExtension);
                };
                ActionListener lExit = e12 -> {
                    popUpDowndloadDoc.dispose();
                    popUpDowndloadDoc.setVisible(false);
                    setVisible(true);
                };

                bDowndloadFile.addActionListener(lAcceptExtension);
                bFailDownloadDocument.addActionListener(lExit);
                setVisible(false);
            }
            else{
                JOptionPane.showMessageDialog(null, "S'ha d'especificar el document a descarregar");
            }
        };

        ActionListener lremoveDocument = e -> {
            if(!title.isEmpty() && !author.isEmpty()){

                JLabel txtRemove = new JLabel("Segur que vols eliminar el document?");
                txtRemove.setSize(txtRemove.getWidth(), 50);
                Dimension x1 = txtRemove.getPreferredSize();
                txtRemove.setBounds((500- x1.width)/2, 50, x1.width, x1.height);
                JButton bYes = new JButton("Sí");
                JButton bNo = new JButton("No");

                bYes.setBounds(100, 120, 100, 30);
                bNo.setBounds(300, 120, 100, 30);
                popUpRemove.add(txtRemove);
                popUpRemove.add(bNo);
                popUpRemove.add(bYes);

                popUpRemove.setResizable(false);
                popUpRemove.setVisible(true);

                ActionListener lYes = e13 -> {
                    txtRemove.setText("");
                    popUpRemove.dispose();
                    popUpRemove.setVisible(false);
                    try {
                        presentationController.removeDocument(title, author);
                        listTable(presentationController.getAllDocuments());

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                    title = "";
                    author = "";
                    activeSelection();
                };

                ActionListener lNo = e14 -> {
                    txtRemove.setText("");
                    popUpRemove.dispose();
                    popUpRemove.setVisible(false);
                };
                bYes.addActionListener(lYes);
                bNo.addActionListener(lNo);
            }
            else{
                JOptionPane.showMessageDialog(null, "S'ha d'especificar el document a eliminar");
            }
        };

        ActionListener loadFile = e -> {
            UIManager.put("FileChooser.openButtonText", "Obrir");
            UIManager.put("FileChooser.cancelButtonText", "Cancel·lar");
            try {
                presentationController.viewLoadDocument();
                listTable(presentationController.getAllDocuments());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        };

        ActionListener lprefixfilter = e -> {
            toolBarSearch.prefixAuthors.setText("");
            String search = toolBarSearch.prefixTitles.getText();
            if(!search.isEmpty()) {
                if(!presentationController.getFirstQuery()){
                    JLabel txtQuery = new JLabel("Vols aplicar la consulta sobre el resultat anterior?");
                    txtQuery.setSize(txtQuery.getWidth(), 50);
                    Dimension x1 = txtQuery.getPreferredSize();
                    txtQuery.setBounds((500- x1.width)/2, 50, x1.width, x1.height);
                    JButton bYes = new JButton("Sí");
                    JButton bNo = new JButton("No");

                    bYes.setBounds(100, 120, 100, 30);
                    bNo.setBounds(300, 120, 100, 30);
                    popUpQueries.add(txtQuery);
                    popUpQueries.add(bNo);
                    popUpQueries.add(bYes);

                    popUpQueries.setResizable(false);
                    popUpQueries.setVisible(true);

                    ActionListener lYes = e13 -> {
                        popUpQueries.dispose();
                        popUpQueries.setVisible(false);
                        listTable(presentationController.getTitlesByPrefix(search, false));
                        repaint();
                    };

                    ActionListener lNo = e14 -> {
                        popUpQueries.dispose();
                        popUpQueries.setVisible(false);
                        listTable(presentationController.getTitlesByPrefix(search, true));
                        repaint();
                    };
                    bYes.addActionListener(lYes);
                    bNo.addActionListener(lNo);
                }
                else{
                    listTable(presentationController.getTitlesByPrefix(search, true));
                    repaint();
                }
            }else{
                listTable(presentationController.getAllDocuments());
            }
        };

        ActionListener lprefixAuthorsfilter = e -> {
            toolBarSearch.prefixTitles.setText("");
            String search = toolBarSearch.prefixAuthors.getText();
            if(!search.isEmpty()){
                ArrayList<String> authorsByPrefix = presentationController.getAuthorsByPrefix(search, "0");
                ArrayList<String> resultTitles = new ArrayList<>();
                for(String authorName : authorsByPrefix){
                    ArrayList<String> titlesByAuthor = new ArrayList<>();
                    try {
                        titlesByAuthor = presentationController.getTitlesByAuthor(authorName);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                    for(String titleByAuthor : titlesByAuthor) resultTitles.add(titleByAuthor + ">" + authorName);
                }
                listTable(resultTitles);
                repaint();
            }else{
                listTable(presentationController.getAllDocuments());
            }
        };

        ActionListener lrestartTable = e -> {
            listTable(presentationController.getAllDocuments());
            toolBarSearch.prefixTitles.setText("");
            toolBarSearch.prefixAuthors.setText("");
            presentationController.setFirstQuery();
        };

        ActionListener lquery = e -> {
            String sQuery = (String) toolBar.jQueries.getSelectedItem();
            switch(Objects.requireNonNull(sQuery)){
                case "Obtenir documents similars":
                    toolBarSearch.prefixTitles.setText("");
                    toolBarSearch.prefixAuthors.setText("");
                    if(!title.isEmpty() && !author.isEmpty()){
                        JLabel insertK = new JLabel("Quants documents semblants vols?");
                        SpinnerModel spinner = new SpinnerNumberModel(0, 0, 10000000, 1);
                        JSpinner txtK = new JSpinner(spinner);
                        txtK.setEditor(new JSpinner.DefaultEditor(txtK));
                        txtK.setBounds(50, 50, 75, 30);
                        insertK.setBounds(50, 20, insertK.getPreferredSize().width, insertK.getPreferredSize().height);
                        JButton bExitFailRemove = new JButton("Sortir");
                        bExitFailRemove.setVisible(true);
                        bExitFailRemove.setBounds(225, 110, 100, 30);
                        JButton bAcceptK = new JButton("Cerca");
                        bAcceptK.setVisible(true);
                        bAcceptK.setBounds(75, 110, 100, 30);

                        popUpSimilar.add(txtK);
                        popUpSimilar.add(insertK);
                        popUpSimilar.add(bExitFailRemove);
                        popUpSimilar.add(bAcceptK);
                        popUpSimilar.setResizable(false);
                        popUpSimilar.setVisible(true);
                        popUpSimilar.repaint();

                        ActionListener lkValue = e15 -> {
                            int knumber = (int) txtK.getValue();
                            txtK.setValue(0);
                            try {
                                ArrayList<String> resSim = presentationController.getSimilarDocuments(title, author, knumber);
                                listTable(resSim);
                                repaint();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                            }
                            popUpSimilar.dispose();
                            popUpSimilar.setVisible(false);

                        };
                        ActionListener lCancel = e16 -> {
                            popUpSimilar.dispose();
                            popUpSimilar.setVisible(false);
                        };
                        bExitFailRemove.addActionListener(lCancel);
                        bAcceptK.addActionListener(lkValue);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "S'ha d'especificar el document per la consulta");
                    }
                    break;

                case "Consulta booleana":
                    toolBarSearch.prefixTitles.setText("");
                    toolBarSearch.prefixAuthors.setText("");
                    JTextField txtK = new JTextField();
                    JLabel insertK = new JLabel("Inserta una expressió booleana: ");
                    txtK.setBounds(50, 50, 200, 20);
                    insertK.setBounds(50, 10, 300, 30);
                    JButton bAcceptBoolean = new JButton("Acceptar");
                    JButton bCancelBoolean = new JButton("Sortir");
                    bCancelBoolean.setVisible(true);
                    bAcceptBoolean.setBounds(75, 110, 100, 30);
                    bCancelBoolean.setBounds(225, 110, 100, 30);
                    popUpSelectDoc.add(txtK);
                    popUpSelectDoc.add(insertK);
                    popUpSelectDoc.add(bAcceptBoolean);
                    popUpSelectDoc.add(bCancelBoolean);
                    popUpSelectDoc.setResizable(false);
                    popUpSelectDoc.setVisible(true);
                    popUpSelectDoc.repaint();

                    ActionListener lnewExpression = e19 -> {
                        newExpression = txtK.getText();
                        boolean firstQuery = presentationController.getFirstQuery();
                        if(presentationController.getFirstQuery()){
                            try {
                                ArrayList<String> resultBooleans = presentationController.getTitlesByBoolean(newExpression, true);
                                listTable(resultBooleans);
                                repaint();
                                newExpression = "";
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                            }
                        }
                        popUpSelectDoc.dispose();
                        popUpSelectDoc.setVisible(false);
                        txtK.setText("");
                        if(!firstQuery){
                            JLabel txtQuery = new JLabel("Vols aplicar la consulta sobre el resultat anterior?");
                            txtQuery.setSize(txtQuery.getWidth(), 50);
                            Dimension x12 = txtQuery.getPreferredSize();
                            txtQuery.setBounds((500- x12.width)/2, 50, x12.width, x12.height);
                            JButton bYes = new JButton("Sí");
                            JButton bNo = new JButton("No");

                            bYes.setBounds(100, 120, 100, 30);
                            bNo.setBounds(300, 120, 100, 30);
                            popUpQueries.add(txtQuery);
                            popUpQueries.add(bNo);
                            popUpQueries.add(bYes);

                            popUpQueries.setResizable(false);
                            popUpQueries.setVisible(true);

                            ActionListener lYes = e17 -> {
                                popUpQueries.dispose();
                                popUpQueries.setVisible(false);
                                try {
                                    ArrayList<String> resultBooleans = presentationController.getTitlesByBoolean(newExpression, false);
                                    listTable(resultBooleans);
                                    repaint();
                                    newExpression = "";
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, ex.getMessage());
                                }
                            };

                            ActionListener lNo = e18 -> {
                                popUpQueries.dispose();
                                popUpQueries.setVisible(false);
                                try {
                                    ArrayList<String> resultBooleans = presentationController.getTitlesByBoolean(newExpression, true);
                                    listTable(resultBooleans);
                                    repaint();
                                    newExpression = "";
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, ex.getMessage());
                                }
                            };
                            bYes.addActionListener(lYes);
                            bNo.addActionListener(lNo);
                        }
                    };

                    ActionListener lCancel = e110 -> {
                        popUpSelectDoc.dispose();
                        popUpSelectDoc.setVisible(false);
                        txtK.setText("");
                    };

                    bAcceptBoolean.addActionListener(lnewExpression);
                    txtK.addActionListener(lnewExpression);
                    bCancelBoolean.addActionListener(lCancel);


                    break;
                default:
                    break;
            }
            toolBar.jQueries.setSelectedIndex(-1);
        };

        ActionListener lauthors = e -> {
            String sAuthor = (String) toolBarSearch.jAuthors.getSelectedItem();
            if(!Objects.equals(sAuthor, toolBarSearch.jAuthors.getItemAt(-1))) {
                ArrayList<String> docsByAut = new ArrayList<>();
                ArrayList<String> resultDocsByAut = new ArrayList<>();
                try {
                    docsByAut = presentationController.getTitlesByAuthor(sAuthor);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                for (String doc : docsByAut) {
                    resultDocsByAut.add(doc + ">" + sAuthor);
                }
                listTable(resultDocsByAut);
                toolBarSearch.prefixTitles.setText("");
                toolBarSearch.prefixAuthors.setText("");
            }
        };

        ActionListener lvisiblefalse = e -> setVisible(false);

        activeSelection();

        ActionListener lbooleanExpressions = e -> {
            presentationController.viewBooleanExpressions();
            setVisible(false);
        };

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                presentationController.manageDummyMetadata();
                System.exit(0);
            }
        });


        toolBarSearch.prefixTitles.addActionListener(lprefixfilter);
        toolBarSearch.prefixAuthors.addActionListener(lprefixAuthorsfilter);
        toolBar.bCreateDoc.addActionListener(lvisiblefalse);
        toolBar.bEditContent.addActionListener(leditDocument);
        toolBarClock.bRestartTable.addActionListener(lrestartTable);
        toolBar.bDownloadDoc.addActionListener(ldownloadDocument);
        toolBar.bVisualizeDoc.addActionListener(lvisualizeDocument);
        toolBar.bRemoveDoc.addActionListener(lremoveDocument);
        toolBar.bLoadFile.addActionListener(loadFile);
        toolBarClock.bBooleanExpressions.addActionListener(lvisiblefalse);
        toolBar.jQueries.addActionListener(lquery);
        toolBarSearch.jAuthors.addActionListener(lauthors);
        toolBarClock.bBooleanExpressions.addActionListener(lbooleanExpressions);

    }

}
