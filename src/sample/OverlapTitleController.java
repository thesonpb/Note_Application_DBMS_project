package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OverlapTitleController {
    public Button YesButton;
    public Button NoButton;
    private String overlapTitle = new String();

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) (NoButton.getScene().getWindow());
        stage.close();
    }

    public void Replace(ActionEvent actionEvent) {
        Controller controller1 = (Controller) Main.loader.getController();
        controller1.replaceContent(Controller.openingNote.getNcontent(), overlapTitle);
        Stage stage = (Stage) (YesButton.getScene().getWindow());
        stage.close();
        ControllerForSaveAsWindow controllerForSaveAsWindow = (ControllerForSaveAsWindow) Controller.loader.getController();
        controllerForSaveAsWindow.closeWindow();
    }

    public void getOverlapTitle(String ntitle) {
        this.overlapTitle = ntitle;
    }
}
