package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerForSaveAsWindow implements Initializable {

    public Button SaveAsButton;
    public TextField TitleTextField;
    String titleDraft = null;
    Note thisNote = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TitleTextField.setText(Controller.openingNote.getNtitle());
        TitleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            titleDraft = newValue;
        });
    }

    public void insertToDatabaseTitleAndContent(ActionEvent actionEvent) {
        Controller controller2 = (Controller) Main.loader.getController();
        thisNote = controller2.getOpeningNote();
        thisNote.setNtitle(titleDraft);
        if (thisNote.getNcontent() == null) thisNote.setNcontent("");
        Controller controller1 = (Controller) Main.loader.getController();
        if (thisNote.getNtitle() == null) return;
        controller2.removeItemFromNoteList(thisNote.getNtitle());
        //change Note content in sql to textContent.getText() ===CHECKED===
        //sau phải đổi idNote thành Ntitle vì đây là lưu 1 file mới nên là lúc hỏi nhập tên file sẽ lấy Ntitle từ ô nhập sau đó tìm trong csdl
        try {
            //!!!check xem có bị trùng title không đã rồi mới cho lưu
            if (NoteDao.isOverlapTitle(thisNote.getNtitle())) {
                System.out.println(thisNote.getNtitle());
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("OverlapTitle.fxml"));
                    Parent root = loader.load();
                    OverlapTitleController controller = (OverlapTitleController) loader.getController();
                    controller.getOverlapTitle(thisNote.getNtitle());
                    Stage stage = new Stage();
                    stage.setTitle("");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    System.out.println("Cant load new window");
                }
            }

            else {
                String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
//                if (textContentDraft != null) {
//                    openingNote.setNcontent(textContentDraft);
//                } else openingNote.setNcontent("");
                thisNote.setNdateCreated(date);
                thisNote.setNtag("");
                Controller.noteDao.saveNote(thisNote);
                controller1.addNewItemToNoteList(thisNote.getNtitle());
                Stage stage = (Stage) (SaveAsButton.getScene().getWindow());
                stage.close();
            }
        } catch (Exception e) {
            Controller.logger.log(Level.SEVERE, e.getMessage());
        }
    }
    public void closeWindow() {
        Stage stage = (Stage) (SaveAsButton.getScene().getWindow());
        stage.close();
    }
}
