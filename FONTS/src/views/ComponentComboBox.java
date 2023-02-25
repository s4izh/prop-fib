package views;

import javax.swing.*;
import java.awt.*;

class ComponentComboBox extends JLabel implements ListCellRenderer {
    /**
     * Nom principal del component.
     */
    private final String _title;

    /**
     * Constructora del component.
     * @param title nom del component que es crearà.
     */
    public ComponentComboBox(String title) {
        _title = title;
    }

    /**
     * Funció per mostrar el nom principal del component.
     * @param list llista dels strings que es mostren als desplegables.
     * @param value valor retornat de la posició índex.
     * @param index índex de les files del desplegable.
     * @param isSelected indica cert si la fila especificada es troba seleccionada.
     * @param hasFocus indica cert si la fila especificada té el focus.
     * @return
     */
   @Override
   public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean hasFocus) {
        if (index == -1 && value == null)
            setText(_title);
        else
            setText(value.toString());
        return this;
    }
}