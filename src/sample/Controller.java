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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    private MediaFileDao mediaFileDao = new MediaFileDao();
    public static final Logger logger = Logger.getLogger(Controller.class.getName());
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
    public static FXMLLoader loader;

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
                stage.show();
            } catch (Exception e) {
                System.out.println("Cant load new window");
            }
        }
        openingNote = new Note();
        textContent.setText("");
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
                stage.show();
            } catch (Exception e) {
                System.out.println("Cant load new window");
            }
        }
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
            Long Msize = file.length();
            System.out.println(Mname);
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Image.fxml"));
            Parent root = loader.load();
            ImageController imageController = (ImageController) loader.getController();
            imageController.loadImage(imageView);
            System.out.println(imageView.getFitWidth());
            Stage stage = new Stage();
            stage.setTitle("Image");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Cant load new window");
        }
    }
}
