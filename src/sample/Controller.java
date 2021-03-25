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
import javafx.stage.Stage;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    private NoteDao noteDao = new NoteDao();

    public MenuItem newNote;
    public MenuItem open;
    public TextArea textContent;
    public ListView attachmentContent; //mediaFile from database
    public AnchorPane newNoteWindow;
    public MenuItem closeMenuItem;
    public MenuItem saveMenuItem;
    public MenuItem quitMenuItem;
    public Button saveButton;
    public ListView<String> noteList; //Ntitle from database
    public TextField fileNameTextField;
    public MenuItem saveAsMenuItem;
    public Button testAddNoteButton;

    ObservableList<String> data = FXCollections.observableArrayList();

    public void createNewNote(ActionEvent actionEvent) {
        //delete everything on the NotecontentField
        textContent.setText("ddd");
        //add "untitled" item to the notelist
    }

    public void showTextContentAndAttachment(ActionEvent actionEvent) {
        textContent.setText("text opened");
        //attachmentContent. (get items for this and show those items)
    }

    public void closeNote(ActionEvent actionEvent) {
        //delete everything on the NoteContentField
        textContent.setText("Note closed");
    }

    public void saveNote(ActionEvent actionEvent) {
        //pop up a window for inserting file name and save button
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

        textContent.setText("Note saved");
        //change Note content in sql to textContent.getText()



    }

    public void quitApp(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void saveNoteAndAddToListView(ActionEvent actionEvent) throws NullPointerException {
        //close the newNoteWindow
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
        //add the note saved to the listview in the notelist section


    }

    public void saveTextContentToTheFile(ActionEvent actionEvent) {
        //update Ncontent and save to database
    }

    public void testAddNote(ActionEvent actionEvent) throws SQLException {
        try {
            Note note = new Note(9, "Test Title", "test", "this is the note to test the addNote", "2021/03/25");
            int id = noteDao.saveNote(note);
            if (id > 0) System.out.println("save successfully");
            else System.out.println("failed");
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
