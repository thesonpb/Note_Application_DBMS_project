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
    String textContentDraft = null;
    Note openingNote = Controller.openingNote;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TitleTextField.setText(openingNote.getNtitle());
        TitleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            textContentDraft = newValue;
        });
    }

    public void insertToDatabaseTitleAndContent(ActionEvent actionEvent) {
        Controller controller1 = (Controller) Main.loader.getController();
        openingNote.setNtitle(textContentDraft);
        //change Note content in sql to textContent.getText() ===CHECKED===
        //sau phải đổi idNote thành Ntitle vì đây là lưu 1 file mới nên là lúc hỏi nhập tên file sẽ lấy Ntitle từ ô nhập sau đó tìm trong csdl
        try {
            //!!!check xem có bị trùng title không đã rồi mới cho lưu
            if (NoteDao.isOverlapTitle(openingNote.getNtitle()))
                System.out.println("đổi tên đi");//pop up cửa sổ đổi tên
            else {
                String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
//                if (textContentDraft != null) {
//                    openingNote.setNcontent(textContentDraft);
//                } else openingNote.setNcontent("");
                openingNote.setNdateCreated(date);
                openingNote.setNtag("");
                Controller.noteDao.saveNote(openingNote);
                controller1.addNewItemToNoteList(openingNote.getNtitle());
                Stage stage = (Stage) (SaveAsButton.getScene().getWindow());
                stage.close();
            }
        } catch (Exception e) {
            Controller.logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
