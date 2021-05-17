package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.logging.Level;

public class DoYouWanToSaveChangeController {
    public Button ConfirmSaveButton;
    public Button DontSaveButton;
    public Button CancelButton;

    public void Cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) (CancelButton.getScene().getWindow());
        stage.close();
    }

    public void DontSave(ActionEvent actionEvent) {
        Controller controller1 = (Controller) Main.loader.getController();
        controller1.setTextToTextField("");
        Stage stage = (Stage) (DontSaveButton.getScene().getWindow());
        stage.close();
    }

    public void SaveContent(ActionEvent actionEvent) {
        Controller controller1 = (Controller) Main.loader.getController();
        try {
            Controller.noteDao.saveTextContent(controller1.textContentDraft, Controller.openingNote.getNtitle());
            System.out.println(Controller.openingNote.getNtitle());
        } catch (Exception e) {
            Controller.logger.log(Level.SEVERE, e.getMessage());
        }
        Stage stage = (Stage) (ConfirmSaveButton.getScene().getWindow());
        stage.close();
    }
}
