package views;

import presentation.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class ViewDownloadFile{
    /**
     * Finestra de selecció de l'arxiu que es vol carregar al nostre full de càlcul
     */
    JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    /**
     * Instància del PresentationController.
     */
    private static final PresentationController presentationController = PresentationController.getInstance();

    /**
     * Constructora de la finestra de carregar document
     */
    public ViewDownloadFile(String title, String author, String extension) {
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Selecciona directori");
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            presentationController.downloadFile(title, author, extension, file.getAbsolutePath());
            presentationController.visibleViewMenu();
        } else if (returnValue == JFileChooser.CANCEL_OPTION) {
            presentationController.visibleViewMenu();
        }
    }


}
