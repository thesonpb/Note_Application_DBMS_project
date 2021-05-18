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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.beans.IntrospectionException;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    public AnchorPane saveNoteWindow;
    public static NoteDao noteDao = new NoteDao();
    public static Note openingNote = new Note();
    public Text date;
    private MediaFileDao mediaFileDao = new MediaFileDao();
    public static final Logger logger = Logger.getLogger(Controller.class.getName());
    public MenuItem addAttachmentMenuItem;
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
    public static FXMLLoader loader;

    public static ObservableList<ImageView> imageData = FXCollections.observableArrayList();
    public static ObservableList<String> data = FXCollections.observableArrayList();
    String textContentDraft = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        noteDao.getAllNoteToData();
        noteList.getItems().addAll(data);
        textContent.textProperty().addListener((observable, oldValue, newValue) -> {
            textContentDraft = newValue;
        });
    }

    public void createNewNote(ActionEvent actionEvent) {
        textContent.setText("");
        openingNote.setNtitle("untitled");
        int i = 1;
        while (NoteDao.isOverlapTitle(openingNote.getNtitle())) {
            openingNote.setNtitle("untitled" + i);
            i++;
        }
        openingNote.setNtag("");
        openingNote.setNcontent("");
        noteList.getItems().add(openingNote.getNtitle());
        System.out.println(openingNote.getNtitle());
    }

    public void closeNote(ActionEvent actionEvent) {
        if (openingNote == null) return;
        if (!noteDao.getNtitleArray().contains(openingNote.getNtitle())) {
            if (openingNote.getNtitle() == null) {
                System.out.println("title null");
                return;
            }
            noteList.getItems().remove(openingNote.getNtitle());
            openingNote.setNcontent(textContent.getText());
            try {
                loader = new FXMLLoader(getClass().getResource("saveAsWindow.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.getIcons().add(new Image(getClass().getResourceAsStream("save as icon.png")));
                stage.setTitle("SAVE AS");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                System.out.println("Cant load new window");
            }

        } else if (!noteDao.getTextContent(openingNote.getNtitle()).equals(textContentDraft)) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("DoYouWantToSaveChange.fxml"));
                Stage stage = new Stage();
                stage.setTitle("");
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (Exception e) {
                System.out.println("Cant load new window");
            }
        } else {
            textContent.setText("");
            openingNote = new Note();
            attachmentContent.getItems().clear();
            imageData.clear();
        }

    }

    public void saveNote(ActionEvent actionEvent) {
        if (openingNote.getNtitle() == null) {
            System.out.println("title null");
            return;
        }
        openingNote.setNcontent(textContentDraft);
        try {
            loader = new FXMLLoader(getClass().getResource("saveAsWindow.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("save as icon.png")));
            stage.setTitle("SAVE AS");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load new window");
        }


    }

    public void quitApp(ActionEvent actionEvent) {
        if (!noteDao.getNtitleArray().contains(openingNote.getNtitle())) {
            if (openingNote.getNtitle() == null) {
                System.out.println("title null");
                return;
            }
            noteList.getItems().remove(openingNote.getNtitle());
            openingNote.setNcontent(textContent.getText());
            try {
                loader = new FXMLLoader(getClass().getResource("saveAsWindow.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.getIcons().add(new Image(getClass().getResourceAsStream("save as icon.png")));
                stage.setTitle("SAVE AS");
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (Exception e) {
                System.out.println("Cant load new window");
            }

        } else if (!noteDao.getTextContent(openingNote.getNtitle()).equals(textContentDraft)) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("DoYouWantToSaveChange.fxml"));
                Stage stage = new Stage();
                stage.setTitle("");
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (Exception e) {
                System.out.println("Cant load new window");
            }
        }
        System.exit(0);
    }


    public void saveTextContentToTheFile(ActionEvent actionEvent) {
        if (openingNote.getNtitle() == null) {
            System.out.println("title null");
            return;
        }
        if (!noteDao.getNtitleArray().contains(openingNote.getNtitle())) {
            if (openingNote.getNtitle() == null) {
                System.out.println("title null");
                return;
            }
            noteList.getItems().remove(openingNote.getNtitle());
            openingNote.setNcontent(textContentDraft);
            try {
                loader = new FXMLLoader(getClass().getResource("saveAsWindow.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.getIcons().add(new Image(getClass().getResourceAsStream("save as icon.png")));
                stage.setTitle("SAVE AS");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                System.out.println("Cant load new window");
            }

        } else {
            try {
                noteDao.saveTextContent(textContentDraft, openingNote.getNtitle());
                openingNote.setNcontent(textContentDraft);
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
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
            long Msize = file.length();
            String NoteTitle = openingNote.getNtitle();
            MediaFileDao.insertIntoDatabase(Mname, Mlink, Msize, NoteTitle);
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);

            //add and show that file on attachmentListView
            attachmentContent.getItems().add(imageView);
        } catch (Exception e) {
            System.out.println("Cant load new window");
        }

    }

    public void displayTextContent(MouseEvent mouseEvent) {
        String title = noteList.getSelectionModel().getSelectedItem();
        openingNote.setNtitle(title);
        openingNote.setNcontent(noteDao.getTextContent(openingNote.getNtitle()));
        textContent.setText(NoteDao.getTextContent(title));
        date.setText(noteDao.getDate(openingNote.getNtitle()));
        attachmentContent.getItems().clear();
        imageData.clear();
        mediaFileDao.getAllImageToImageData(openingNote.getNtitle());
        attachmentContent.getItems().addAll(imageData);
    }

    public void addNewItemToNoteList(String s) {
        noteList.getItems().add(s);
    }

    public void setTextToTextField(String s) {
        textContent.setText(s);
    }

    public void replaceContent(String content, String title) {
        try {
            noteDao.saveTextContent(content, title);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Note getOpeningNote() {
        return openingNote;
    }

    public void removeItemFromNoteList(String ntitle) {
        noteList.getItems().remove(openingNote.getNtitle());
    }

    public void displayImage(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) attachmentContent.getSelectionModel().getSelectedItem();
        Integer index = attachmentContent.getItems().indexOf(imageView);
        attachmentContent.getItems().remove(imageView);
        imageView.setFitWidth(800);
        imageView.setPreserveRatio(true);
        BorderPane pane;
        Scene scene;
        Stage stage;
        pane = new BorderPane();
        pane.setCenter(imageView);
        scene = new Scene(pane);

        stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        attachmentContent.getItems().add(index, imageView);
        attachmentContent.getSelectionModel().clearSelection();
    }

    public void showAboutWindow(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Image.fxml"));
            Stage stage = new Stage();
            stage.setTitle("About this");
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("noteIcon.png")));
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println("Cant load new window");
        }
    }
}
