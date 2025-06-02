package com.example.cp_library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    public ObservableList<Book> books;
    public TableView<Book> bookTable;
    public Label message;
    User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = Storage.selectedUser;
        books = FXCollections.observableList(user.getBooks());
        bookTable.setItems(books);
    }

    public void reserveBook(ActionEvent actionEvent) throws IOException {
        Transporter.openWindow((Stage) message.getScene().getWindow(), "user-reserve.fxml");
    }

    public void editAccount(ActionEvent actionEvent) throws IOException {
        Storage.last = "user";
        Transporter.openWindow((Stage) message.getScene().getWindow(), "edit-userU.fxml");
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Transporter.openWindow((Stage) message.getScene().getWindow(), "login.fxml");
    }

}
