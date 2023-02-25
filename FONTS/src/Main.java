import presentation.PresentationController;

import java.io.IOException;

public class Main {
    private static final PresentationController presentationController = PresentationController.getInstance();
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        presentationController.iniPresentation();
    }
}