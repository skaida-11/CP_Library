package com.example.cp_library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class BookAdmin implements Initializable {
    public Button backButton;
    public TableView<Book> bookTable;
    public Label message;
    public ChoiceBox<String> sortBy;
    public CheckBox onlyAvailable;
    public ObservableList<Book> books;
    public FilteredList<Book> filteredBooks;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sortBy.getItems().addAll("Author", "Title");
        books = FXCollections.observableList(Storage.allBooks);
        filteredBooks = new FilteredList<>(books, book -> true);
        bookTable.setItems(filteredBooks);
        onlyAvailable.setSelected(false);
    }

    @FXML
    public void moveToAddBook(ActionEvent actionEvent) throws IOException {
        Transporter.openWindow(
                (Stage) backButton.getScene().getWindow(),"add-book.fxml");
    }

    public void moveToEditBook(ActionEvent actionEvent) throws IOException {
        Storage.selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (Storage.selectedBook == null) {
            message.setText("Please choose book to edit");
        } else {
            Transporter.openWindow((Stage) bookTable.getScene().getWindow(),"edit-book.fxml");
        }
    }

    public void moveToDeleteBook(ActionEvent actionEvent) throws IOException {
        Storage.selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (Storage.selectedBook == null) {
            message.setText("Please choose book to delete");
            return;
        }

        if (!Storage.selectedBook.isAvailable()) {
            message.setText("This book is currently borrowed");
            return;
        }
        Stage base = (Stage) message.getScene().getWindow();
        boolean deleteSelectedBook = Transporter.confirm(base, Storage.selectedBook.getAuthor(), Storage.selectedBook.getTitle(), "Delete");

        if (!deleteSelectedBook){
            message.setText("Deletion canceled");
            return;
        }
            deleteBook(Storage.selectedBook);
            message.setText("Book deleted");
    }

    public void deleteBook(Book thisBook) {
        books.remove(thisBook);
        Storage.allBooks.removeIf(b -> b.getIsbn().equals(thisBook.getIsbn()));
        Storage.writeToFile("bookList", Storage.allBooks);
    }

    public void onlyAvailable(ActionEvent actionEvent) {
        if (onlyAvailable.isSelected()) {
            filteredBooks.setPredicate(Book::isAvailable);
        } else {
            filteredBooks.setPredicate(book -> true);
        }
    }

    public void sortSelected(ActionEvent actionEvent) {
        if ("Author".equals(sortBy.getValue())) {
            books.sort(Comparator.comparing(Book::getAuthor));
        } else if ("Title".equals(sortBy.getValue())) {
            books.sort(Comparator.comparing(Book::getTitle));
        }
    }

    public void backToAdmin(ActionEvent actionEvent) throws IOException {
        Transporter.openWindow((Stage) backButton.getScene().getWindow(),"admin.fxml");
    }
}
