package com.example.cp_library;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class BookAdd {

    public TextField authorField;
    public TextField titleField;
    public TextField isbnField;
    public Label addMessage;
    public Button backButton;

    public void addNewBook(ActionEvent actionEvent) {
        String isbn = isbnField.getText();
        String author = authorField.getText();
        String title = titleField.getText();

        if (isbn == null || isbn.trim().isEmpty() ||
                author == null || author.trim().isEmpty() ||
                title == null || title.trim().isEmpty()) {
            addMessage.setText("Please fill all fields");
            return;
        }
        if (!Verification.isbnCorrect(isbn)) {
            addMessage.setText("Please check ISBN number");
            return;
        }
        if (!Verification.isbnUnique(isbn)) {
            addMessage.setText("This book is already registered in library");
            return;
        }
        Book newBook = new Book(authorField.getText(), titleField.getText(), isbnField.getText());
        Storage.allBooks.add(newBook);
        addMessage.setText("Book added to library successfully");
    }

    public void backToBook(ActionEvent actionEvent) throws IOException {
        Transporter.openWindow((Stage) addMessage.getScene().getWindow(), "admin-book.fxml");
    }
}
