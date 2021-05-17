package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ImageController {


    public Button ok;

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) (ok.getScene().getWindow());
        stage.close();
    }
}
