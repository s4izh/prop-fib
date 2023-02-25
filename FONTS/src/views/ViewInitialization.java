package views;

import presentation.PresentationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ViewInitialization extends JFrame {

    /**
     * Instància del PresentationController.
     */
    PresentationController presentationController = PresentationController.getInstance();

    /**
     * Funció per crear i posar una icona a un JLabel.
     * @param path Path de l'imatge.
     * @return Una icona d'imatge.
     */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ViewMenu.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + "../icon/gift-box.png");
            return null;
        }
    }

    /**
     * Constructora de la vista inicial de l'aplicació si no s'obre per primer cop.
     */
    public ViewInitialization(){

        setTitle("GESTOR DE DOCUMENTS");
        setSize(600, 300);
        setLayout(null);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        JLabel newIcon = new JLabel(createImageIcon("../icon/dog.gif"));
        newIcon.setBounds(0, 0, 100, 100);
        JLabel catIcon = new JLabel(createImageIcon("../icon/cat.gif"));
        catIcon.setBounds(500, 175, 100, 100);
        JLabel cloudIcon = new JLabel(createImageIcon("../icon/cloud.png"));
        cloudIcon.setBounds(500, 0, 100, 100);
        JLabel santaIcon = new JLabel(createImageIcon("../icon/chimenea.png"));
        santaIcon.setBounds(0, 175, 100, 100);

        JLabel txtInitialization = new JLabel("Vols utilitzar les dades de l'última sessió?");
        txtInitialization.setSize(txtInitialization.getWidth(), 50);
        Dimension x = txtInitialization.getPreferredSize();
        txtInitialization.setBounds((600-x.width)/2, 50, x.width, x.height);

        JButton bYesAnswer = new JButton("Sí");
        bYesAnswer.setBounds(100, 150, 100, 30);
        JButton bNoAnswer = new JButton("No");
        bNoAnswer.setBounds(400, 150, 100, 30);

        add(txtInitialization);
        add(bNoAnswer);
        add(bYesAnswer);
        add(newIcon);
        add(catIcon);
        add(cloudIcon);
        add(santaIcon);

        setResizable(false);
        setVisible(true);

        ActionListener lYes = e -> {
            dispose();
            setVisible(false);
            presentationController.iniPresentationReuse(true);
        };

        ActionListener lNo = e -> {
            dispose();
            setVisible(false);
            presentationController.iniPresentationReuse(false);
        };
        bYesAnswer.addActionListener(lYes);
        bNoAnswer.addActionListener(lNo);

    }
}
