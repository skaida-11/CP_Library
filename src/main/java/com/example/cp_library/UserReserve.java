package com.example.cp_library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserReserve implements Initializable {
    public TableView<Book> bookTable;
    public ObservableList<Book> books;
    public FilteredList<Book> filteredBooks;
    public Label messageReserve;
    public User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        books = FXCollections.observableList(Storage.allBooks);
        filteredBooks = new FilteredList<>(books, book -> true);
        bookTable.setItems(filteredBooks);
        currentUser = Storage.selectedUser;
    }

    public void reserveBook(ActionEvent actionEvent) {
        Storage.selectedBook = bookTable.getSelectionModel().getSelectedItem();
        if (Storage.selectedBook == null) {
            messageReserve.setText("Please choose book to edit");
        } else {
            boolean resBo = Transporter.confirm((Stage) messageReserve.getScene().getWindow(), Storage.selectedBook.getAuthor(), Storage.selectedBook.getTitle(), "Reserve book");
            if (!resBo){
                messageReserve.setText("Reservation canceled");
                return;
            }
            makeReservation(Storage.selectedBook);
            messageReserve.setText("Book reserved for you");
        }

    }

    private void makeReservation(Book selectedBook) {
        Storage.selectedBook.setAvailable(false);
        currentUser.setBooksBorrowed(currentUser.getBooksBorrowed() + 1);
        currentUser.getBooks().add(Storage.selectedBook);
        currentUser.setBooks(currentUser.getBooks());
        Storage.writeToFile("bookList", Storage.allBooks);
        Storage.writeToFile("userList", Storage.allUsers);
    }

    public void backToUser(ActionEvent actionEvent) throws IOException {
        Transporter.openWindow((Stage) messageReserve.getScene().getWindow(), "user.fxml");
    }
}
