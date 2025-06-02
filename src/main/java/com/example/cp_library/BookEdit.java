package com.example.cp_library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookEdit implements Initializable {
    public TextField editAuthorField;
    public TextField editTitleField;
    public TextField editIsbnField;
    public Label editMessage;
    public Button editButton;
    public ChoiceBox available;

    private String originalIsbn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        available.getItems().addAll(Boolean.TRUE, Boolean.FALSE);

        if (Storage.selectedBook != null) {
            Book editableBook = Storage.selectedBook;
            originalIsbn = editableBook.getIsbn();
            editAuthorField.setText(editableBook.getAuthor());
            editTitleField.setText(editableBook.getTitle());
            editIsbnField.setText(originalIsbn);
            available.setValue(editableBook.isAvailable());
        }
    }

    @FXML
    private void editBook(ActionEvent actionEvent) {
        String author = editAuthorField.getText().trim();
        String title  = editTitleField.getText().trim();
        String newIsbn = editIsbnField.getText().trim();
        Boolean isAvail = (Boolean) available.getValue();

        if (author.isEmpty() || title.isEmpty()) {
            editMessage.setText("Please check all fields");
            return;
        }

        if (!Verification.isbnCorrect(newIsbn)) {
            editMessage.setText("Please check ISBN");
            return;
        }

        if (!newIsbn.equals(originalIsbn) && !Verification.isbnUnique(newIsbn)) {
            editMessage.setText("This book is already in library");
            return;
        }

        for (Book b : Storage.allBooks) {
            if (b.getIsbn().equals(originalIsbn)) {
                b.setAuthor(author);
                b.setTitle(title);
                b.setIsbn(newIsbn);
                b.setAvailable(isAvail);
                break;
            }
        }

        Storage.writeToFile("bookList", Storage.allBooks);
        editMessage.setText("Book edited successfully");
    }

    @FXML
    private void backToBook(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) editButton.getScene().getWindow();
        Transporter.openWindow(stage, "admin-book.fxml");
    }
}
