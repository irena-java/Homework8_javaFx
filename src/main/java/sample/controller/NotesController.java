package main.java.sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import main.java.sample.entity.Note;
import main.java.sample.service.NoteService;
import main.java.sample.service.ServiceException;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class NotesController implements Initializable {

    private final NoteService noteService = new NoteService();

    @FXML
    private TextArea noteDescription;
    @FXML
    private ListView<Note> listViewSelectAll;

    public void createNote() throws ServiceException {
        String noteDescriptionText = noteDescription.getText();
        Note note = new Note();
        note.setCreatedTime(LocalDateTime.now());
        note.setDescription(noteDescriptionText);
        Note createNoteId = noteService.create(note);
        listViewSelectAll.getItems().add(createNoteId);
    }

    public void deleteNote() throws ServiceException {
        Note selectedItem = listViewSelectAll.getSelectionModel().getSelectedItem();
        noteService.delete(selectedItem);
        listViewSelectAll.getItems().remove(selectedItem);
    }

    public void selectAllNotes() throws ServiceException {
        List<Note> findAll = noteService.findAll();
        ObservableList<Note> observableListNotes = FXCollections.observableList(findAll);
        listViewSelectAll.setItems(observableListNotes);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}