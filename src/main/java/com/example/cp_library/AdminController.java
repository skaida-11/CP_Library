package com.example.cp_library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    public Button adminUser;
    public Button backButton;

    @FXML
    public void adminToUser(ActionEvent actionEvent) throws IOException {
        Transporter.openWindow((Stage) adminUser.getScene().getWindow(), "admin-user.fxml");
    }

    public void adminToBook(ActionEvent actionEvent) throws IOException {
        Transporter.openWindow((Stage) adminUser.getScene().getWindow(), "admin-book.fxml");
    }

    public void adminBack(ActionEvent actionEvent) throws IOException {
        Transporter.openWindow((Stage) adminUser.getScene().getWindow(), "login.fxml");
    }
}
