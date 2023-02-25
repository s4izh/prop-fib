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

public class ViewBooleanExpressions extends JFrame {

    /**
     * Instància del PresentationController.
     */
    private static final PresentationController presentationController = PresentationController.getInstance();

    /**
     * Per poder fer scroll.
     */
    private final JScrollPane scrollPaneTable;

    /**
     * Taula amb les expressions booleanes.
     */
    private JTable tableBooleans;

    /**
     * Expressió seleccionada.
     */
    private String selectedExpression = "";

    /**
     * Funció per actualitzar la taula que mostra les expressions booleanes del gestor.
     * @param expressions expressions del gestor.
     */
    public void listTableBooleanExpressions(ArrayList<String> expressions) {
        selectedExpression = "";
        DefaultTableModel model;
        String[] columnNames = new String[]{"Expressions booleanes"};

        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (expressions.size() == 0) {
            String author = "No s'han trobat resultats";
            Object[] aux = {author};
            model.addRow(aux);
        } else {
            for (String word : expressions) {
                Object[] aux = {word};
                model.addRow(aux);
            }
        }

        tableBooleans = new JTable(model);
        tableBooleans.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        tableBooleans.setOpaque(false);
        tableBooleans.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableBooleans.setAutoCreateRowSorter(true);
        scrollPaneTable.setViewportView(tableBooleans);
        activeSelection();
        tableBooleans.clearSelection();
    }

    /**
     * Funció per habilitar la selecció de files en la taula.
     */
    public void activeSelection(){
        tableBooleans.getSelectionModel().addListSelectionListener(event -> {
            selectedExpression = tableBooleans.getValueAt(tableBooleans.getSelectedRow(), 0).toString();
            if(Objects.equals(selectedExpression, "No s'han trobat resultats")) selectedExpression = "";
        });
    }

    /**
     * Funció per crear la constructora de la vista de gestió d'expressions booleanes.
     */
    public ViewBooleanExpressions() {
        JToolBar toolBar = new JToolBar();
        toolBar.setBorder(new EmptyBorder(10, 10, 5, 10));
        JToolBar toolBarExit = new JToolBar();
        toolBarExit.setBorder(new EmptyBorder(5, 10, 5, 10));
        JPanel panelBooleanExpression = new JPanel();
        panelBooleanExpression.setBorder(new EmptyBorder(5, 5, 10, 5));

        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));
        toolBarExit.setLayout(new BoxLayout(toolBarExit, BoxLayout.X_AXIS));
        panelBooleanExpression.setLayout(new BoxLayout(panelBooleanExpression, BoxLayout.Y_AXIS));

        setResizable(true);
        setTitle("GESTOR DE DOCUMENTS");

        scrollPaneTable = new JScrollPane();

        scrollPaneTable.setSize(500, 500);

        listTableBooleanExpressions(presentationController.getBooleanExpressions());
        scrollPaneTable.setBorder(new EmptyBorder(10, 5, 0, 5));
        listTableBooleanExpressions(presentationController.getBooleanExpressions());
        panelBooleanExpression.add(scrollPaneTable);

        toolBar.setOpaque(false);
        toolBar.setBorderPainted(false);
        JButton bUseBooleanExpression = new JButton("Utilitzar expressió");
        toolBar.add(bUseBooleanExpression);
        toolBar.addSeparator();
        JButton bModifyBooleanExpression = new JButton("Modificar expressió");
        toolBar.add(bModifyBooleanExpression);
        toolBar.addSeparator();
        JButton bRemoveBooleanExpression = new JButton("Eliminar expressió");
        toolBar.add(bRemoveBooleanExpression);
        toolBarExit.setFloatable(false);
        toolBarExit.setOpaque(false);
        toolBarExit.setBorderPainted(false);
        JButton bExit = new JButton("Enrere");
        toolBarExit.add(bExit);
        panelBooleanExpression.add(toolBarExit);
        add(toolBar, BorderLayout.PAGE_START);
        add(panelBooleanExpression, BorderLayout.CENTER);
        setSize(700, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        Dimension dim2 = Toolkit.getDefaultToolkit().getScreenSize();
        dim2.width = 550;
        dim2.height = 250;
        setMinimumSize(dim2);

        JDialog popUpModifyExpression = new JDialog(this, "Modificar expressió booleana");
        popUpModifyExpression.setBounds(800, 300, 400, 200);
        popUpModifyExpression.setLayout(null);
        popUpModifyExpression.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        JDialog popUpQueries =  new JDialog(this, "GESTOR DE DOCUMENTS");
        popUpQueries.setSize(500, 200);
        popUpQueries.setLayout(null);
        popUpQueries.setLocation(dim.width/2-250, dim.height/2-100);
        
        ActionListener luseExpression = e -> {
            if (presentationController.existsBooleanExpression(selectedExpression) && !selectedExpression.isEmpty()) {
                boolean firstQuery = presentationController.getFirstQuery();
                if (presentationController.getFirstQuery()) {
                    try {
                        ArrayList<String> resultBooleans = presentationController.getTitlesByBoolean(selectedExpression, true);
                        presentationController.setTableUsingBooleans(resultBooleans);
                        repaint();
                        selectedExpression = "";
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }

                    setVisible(false);
                    presentationController.visibleViewMenu();
                }

                if (!firstQuery) {
                    JLabel txtQuery = new JLabel("Vols aplicar la consulta sobre el resultat anterior?");
                    txtQuery.setSize(txtQuery.getWidth(), 50);
                    Dimension x12 = txtQuery.getPreferredSize();
                    txtQuery.setBounds((500 - x12.width) / 2, 50, x12.width, x12.height);
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
                            ArrayList<String> resultBooleans = presentationController.getTitlesByBoolean(selectedExpression, false);
                            presentationController.setTableUsingBooleans(resultBooleans);
                            repaint();
                            selectedExpression = "";
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }


                        setVisible(false);
                        presentationController.visibleViewMenu();
                    };

                    ActionListener lNo = e18 -> {
                        popUpQueries.dispose();
                        popUpQueries.setVisible(false);
                        try {
                            ArrayList<String> resultBooleans = presentationController.getTitlesByBoolean(selectedExpression, true);
                            presentationController.setTableUsingBooleans(resultBooleans);
                            repaint();
                            selectedExpression = "";
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }


                        setVisible(false);
                        presentationController.visibleViewMenu();
                    };
                    bYes.addActionListener(lYes);
                    bNo.addActionListener(lNo);
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "S'ha d'especificar l'expressió");

                setVisible(false);
                presentationController.visibleViewMenu();
            }
        };

        ActionListener lmodifyExpression = e -> {
            if (presentationController.existsBooleanExpression(selectedExpression)) {
                JTextField txtNewBoolean = new JTextField();
                JLabel insertK = new JLabel("Modifica l'expressió booleana: ");
                txtNewBoolean.setBounds(50, 50, 300, 20);
                insertK.setBounds(50, 10, 300, 30);
                JButton bModificarNewBoolean = new JButton("Modificar");
                JButton bCancelNewBoolean = new JButton("Sortir");
                bCancelNewBoolean.setVisible(true);
                bCancelNewBoolean.setBounds(225, 110, 100, 30);
                bModificarNewBoolean.setBounds(75, 110, 100, 30);
                popUpModifyExpression.add(bModificarNewBoolean);
                popUpModifyExpression.add(txtNewBoolean);
                popUpModifyExpression.add(insertK);
                popUpModifyExpression.add(bCancelNewBoolean);
                popUpModifyExpression.setResizable(false);
                popUpModifyExpression.setVisible(true);
                popUpModifyExpression.repaint();

                ActionListener lselectedExpression = e1 -> {
                    String expression = txtNewBoolean.getText();
                    txtNewBoolean.setText("");
                    if (!presentationController.existsBooleanExpression(expression) && !expression.isEmpty()) {
                        try {
                            presentationController.modifyBooleanExpression(selectedExpression, expression);
                            selectedExpression = "";
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    } else {
                        if (presentationController.existsBooleanExpression(expression)) {
                            JOptionPane.showMessageDialog(null, "L'expressió modificada ja existeix");

                        } else {
                            JOptionPane.showMessageDialog(null, "L'expressió modificada no pot ser buida");
                        }
                        selectedExpression = "";
                    }
                    popUpModifyExpression.dispose();
                    popUpModifyExpression.setVisible(false);
                    listTableBooleanExpressions(presentationController.getBooleanExpressions());
                    activeSelection();
                };

                ActionListener lExitNewBoolean = e12 -> {
                    popUpModifyExpression.dispose();
                    popUpModifyExpression.setVisible(false);
                    listTableBooleanExpressions(presentationController.getBooleanExpressions());
                    selectedExpression = "";
                };

                bModificarNewBoolean.addActionListener(lselectedExpression);
                txtNewBoolean.addActionListener(lselectedExpression);
                bCancelNewBoolean.addActionListener(lExitNewBoolean);

                activeSelection();

                }
                else{
                    JOptionPane.showMessageDialog(null, "S'ha d'especificar l'expressió");
                    }
                };

        ActionListener ldeleteExpression = e -> {
            if(presentationController.existsBooleanExpression(selectedExpression)){
                try {
                    presentationController.deleteBooleanExpression(selectedExpression);
                    selectedExpression = "";
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                listTableBooleanExpressions(presentationController.getBooleanExpressions());
            }
            else{
                JOptionPane.showMessageDialog(null,"S'ha d'especificar l'expressió");
            }
            activeSelection();
        };

        ActionListener lExit = e -> {
            setVisible(false);
            presentationController.visibleViewMenu();
            presentationController.setTableUsingBooleans(presentationController.getAllDocuments());
        };


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                presentationController.manageDummyMetadata();
                System.exit(0);
            }
        });


        activeSelection();

        bUseBooleanExpression.addActionListener(luseExpression);
        bModifyBooleanExpression.addActionListener(lmodifyExpression);
        bRemoveBooleanExpression.addActionListener(ldeleteExpression);
        bExit.addActionListener(lExit);
    }
}