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
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.font.DelegatingShape;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    private NoteDao noteDao = new NoteDao();
    private Note openingNote = new Note();
    private MediaFileDao mediaFileDao = new MediaFileDao();
    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    public MenuItem addAttachmentMenuItem;
    public MenuItem newNote;
    public MenuItem open;
    public TextField textContent;
    public ListView attachmentContent; //mediaFile from database
    public AnchorPane newNoteWindow;
    public MenuItem closeMenuItem;
    public MenuItem saveMenuItem;
    public MenuItem quitMenuItem;
    public Button saveButton;
    public ListView<String> noteList; //Ntitle from database
    public TextField fileNameTextField;
    public MenuItem saveAsMenuItem;

    public boolean isSaved = true;
    public boolean isFirstTimeSaved = true;

    public static ObservableList<String> data = FXCollections.observableArrayList();
    String textContentDraft = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ===CHECKED===
        // lấy tất cả các note trong csdl cho vào data rồi cho vào notelist để hiển thị danh sách note ra
        noteDao.getAllNoteToData();
        noteList.getItems().addAll(data);
        textContent.textProperty().addListener((observable, oldValue, newValue) -> {
            textContentDraft = newValue;
        });
    }

    private void loadData() {

    }

    public void createNewNote(ActionEvent actionEvent) {

        //delete everything on the NotecontentField ===CHECKED===
        textContent.setText("");
        //add "untitled" item to the notelist
        openingNote.setNtitle("untitled");
        openingNote.setNtag("");
        noteList.getItems().add("untitled");
        isSaved = false;
        isFirstTimeSaved = true;
    }

    public void closeNote(ActionEvent actionEvent) {
        //delete everything on the NoteContentField ===CHECKED===
        textContent.setText("Note closed");
        openingNote = new Note();
        //nếu chưa được save lần nào thì sẽ hiện cửa sổ để lưu save as và đổi tên
        //nếu là note đã đc lưu trong csdl thì sẽ pop up cửa sổ để lưu content
//        if (isFirstTimeSaved) ...
//        else if (!isSaved)
    }

    public void saveNote(ActionEvent actionEvent) {
        //pop up a window for inserting file name and save button ===CHECKED===
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newNoteWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.getIcons().add(new Image(getClass().getResourceAsStream("save as icon.png")));
            stage.setTitle("SAVE AS");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load new window");
        }

        //change Note content in sql to textContent.getText() ===CHECKED===
        //sau phải đổi idNote thành Ntitle vì đây là lưu 1 file mới nên là lúc hỏi nhập tên file sẽ lấy Ntitle từ ô nhập sau đó tìm trong csdl
        try {
            //!!!check xem có bị trùng title không đã rồi mới cho lưu
            if (NoteDao.isOverlapTitle(openingNote.getNtitle()))
                System.out.println("đổi tên đi");//pop up cửa sổ đổi tên
            else {
                String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                openingNote.setNcontent(textContentDraft);
                openingNote.setNdateCreated(date);
                noteDao.saveNote(openingNote);
                isSaved = true;
                isFirstTimeSaved = false;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }

    public void quitApp(ActionEvent actionEvent) {
        //nếu chưa được save lần nào thì sẽ hiện cửa sổ để lưu save as và đổi tên
        //nếu là note đã đc lưu trong csdl thì sẽ pop up cửa sổ để lưu content
//        if (isFirstTimeSaved) ...
//        else if (!isSaved)
        System.exit(0);
    }


    public void saveTextContentToTheFile(ActionEvent actionEvent) {
        //update Ncontent and save to database ===CHECKED===
        //sau phải đổi idNote thành idNote của openingNote
        try {
            noteDao.saveTextContent(textContentDraft, openingNote.getNtitle());
            openingNote.setNcontent(textContentDraft);
            isSaved = true;
            isFirstTimeSaved = false;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }


    public void addAttachment(ActionEvent actionEvent) {
        //pop upwindow to select file and get basic information of the attachment and a button to submit ===CHECKED===
        try {
            Stage stage = new Stage();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(stage);
            String Mname = file.getName();
            String Mlink = file.getAbsolutePath();
            Long Msize = file.length();
            System.out.println(Msize);
            //add and show that file on attachmentListView
            attachmentContent.getItems().add(file);
        } catch (Exception e) {
            System.out.println("Cant load new window");
        }

    }

    public void displayTextContent(MouseEvent mouseEvent) {
        String title = noteList.getSelectionModel().getSelectedItem();
        openingNote.setNtitle(title);
        textContent.setText(NoteDao.getTextContent(title));
    }

}
