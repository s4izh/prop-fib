package views;

import presentation.PresentationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ComponentClockBar extends JToolBar{
    /**
     * Instància del PresentationController.
     */
    private final PresentationController presentationController = PresentationController.getInstance();

    /**
     * JButton que permet mostrar tots els documents del gestor a la taula i reinicialitzar els cercadors per títol i autor.
     */
    final JButton bRestartTable = new JButton("Llistar tots els documents", createImageIcon("../icon/restart.png"));

    /**
     * JButton per anar a la vista de gestió d'expressions booleanes.
     */
    final JButton bBooleanExpressions = new JButton("Gestió expressions booleanes", createImageIcon("../icon/settings.png"));

    /**
     * Integer utilitzat per l'actualització del rellotge.
     */
    final int ONE_SECOND = 1000;

    /**
     * SimpleDateFormat per mostrar per pantalla l'hora.
     */
    final SimpleDateFormat clockFormat = new SimpleDateFormat("H:mm:ss");

    /**
     * JLabel on es mostra l'hora actual.
     */
    JLabel clockLabel = new JLabel();

    /**
     * Funció per incloure icones als botons
     * @param path ubicació d'una icona.
     * @return es retorna un ImageIcon de l'icona escollit.
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ViewMenu.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Creadora de la barra inferior de la finestra principal.
     */
    ComponentClockBar(){
        add(bRestartTable);
        addSeparator();
        add(bBooleanExpressions);
        addSeparator();
        add(Box.createGlue());
        setFloatable(false);
        add(clockLabel, BorderLayout.LINE_END);
        Dimension dC = bRestartTable.getPreferredSize();
        dC.width = 10000;
        dC.height = 20;
        setMaximumSize(dC);
        setOpaque(false);
        setBorderPainted(false);

        Timer timer = new Timer(ONE_SECOND, evt -> {
            clockLabel.setText(clockFormat.format(new Date()));
            clockLabel.repaint();
        });
        clockLabel.setText(clockFormat.format(new Date()));
        timer.start();

    }
}
