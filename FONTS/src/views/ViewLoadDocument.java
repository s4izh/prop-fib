package views;

import presentation.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;

public class ViewLoadDocument {
    /**
     * Finestra de selecció de l'arxiu que es vol carregar al nostre sistema.
     */
    JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    /**
     * Instància del PresentationController.
     */
    private static final PresentationController presentationController = PresentationController.getInstance();

    /**
     * Constructora de la finestra de carregar document
     */
    public ViewLoadDocument() {
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileFilter(new FileNameExtensionFilter(null, "txt", "xml", "json"));
        chooser.setDialogTitle("Selecciona fitxer");
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            ArrayList<String> paths = new ArrayList<>();
            File[] files = chooser.getSelectedFiles();
            for(File file : files){
                String path = file.getAbsolutePath();
                paths.add(path);
            }
            presentationController.loadDocument(paths);
        }

    }

}