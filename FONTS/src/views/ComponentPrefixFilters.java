package views;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ComponentPrefixFilters extends JTextField implements FocusListener {
    private Icon icon;
    private final String hint;
    private final Insets dummyInsets;

    /**
     * Constructora d'un cercador.
     * @param jtf JTextField sobre el qual implementar la classe.
     * @param icon icona per mostrar al cercador.
     * @param hint string inicial del cercador.
     */
    public ComponentPrefixFilters(JTextField jtf, String icon, String hint){
        setIcon(createImageIcon("../icon/"+icon+".png",icon));
        this.hint = hint;
        Border border = UIManager.getBorder("TextField.border");
        JTextField dummy = new JTextField();
        this.dummyInsets = border.getBorderInsets(dummy);
        addFocusListener(this);
    }

    public void setIcon(Icon newIcon){
        this.icon = newIcon;
    }

    /**
     * Pinta el cercador.
     * @param g Cercador modificat.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int textX = 2;

        if(this.icon!=null){
            int iconWidth = icon.getIconWidth();
            int iconHeight = icon.getIconHeight();
            int x = dummyInsets.left + 5;
            textX = x+iconWidth+2;
            int y = (this.getHeight() - iconHeight)/2;
            icon.paintIcon(this, g, x, y);
        }

        setMargin(new Insets(2, textX, 2, 2));

        if ( this.getText().equals("")) {
            int height = this.getHeight();
            Font prev = g.getFont();
            Font italic = prev.deriveFont(Font.ITALIC);
            Color prevColor = g.getColor();
            g.setFont(italic);
            g.setColor(UIManager.getColor("textInactiveText"));
            int h = g.getFontMetrics().getHeight();
            int textBottom = (height - h) / 2 + h - 4;
            int x = this.getInsets().left;
            Graphics2D g2d = (Graphics2D) g;
            RenderingHints hints = g2d.getRenderingHints();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.drawString(hint, x, textBottom);
            g2d.setRenderingHints(hints);
            g.setFont(prev);
            g.setColor(prevColor);
        }

    }

    /**
     * Funció per crear la icona del cercador.
     * @param path ubicació de la icona
     * @param description descripció de la icona
     * @return retorna una ImageIcon de la icona.
     */
    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Repinta el component quan s'introdueixen chars.
     * @param arg0 event del cercador.
     */
    @Override
    public void focusGained(FocusEvent arg0) {
        this.repaint();
    }

    /**
     * Repinta el component quan s'introdueixen chars.
     * @param arg0 event del cercador.
     */
    @Override
    public void focusLost(FocusEvent arg0) {
        this.repaint();
    }


}
