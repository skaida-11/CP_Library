package com.example.cp_library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField userName;
    public PasswordField password;
    public Label message;
    public Label restoreMessage;
    public TextField restoreEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Storage.InitUsers();
        Storage.InitBooks();
        Storage.allUsers = new ArrayList<>();
        Storage.allBooks = new ArrayList<>();
        Storage.allUsers = Storage.readFromFile("userList");
        Storage.allBooks = Storage.readFromFile("bookList");
    }

    @FXML
    public void loginButton(ActionEvent actionEvent) throws IOException {
        String name = userName.getText();
        String pass = password.getText();

        if (name.equals("admin") && pass.equals("1234")){
            Transporter.openWindow((Stage) userName.getScene().getWindow(), "admin.fxml");
        } else {
            findUser();
        }
    }

    private void findUser() throws IOException {
        String name = userName.getText();
        String pass = password.getText();

        for (User u : Storage.allUsers){
            if (name.equals(u.getEmail())){
                if (u.isBlocked()){
                    message.setText("User is blocked, please contact admin");
                    return;
                }
                if (pass.equals(u.getPassword())){
                    Storage.selectedUser = u;
                    u.setWrongAttempts(0);
                    Transporter.openWindow((Stage) message.getScene().getWindow(), "user.fxml");
                } else {
                    message.setText("Wrong password. Attempts left: " + (2 - u.getWrongAttempts()));
                    u.setWrongAttempts(u.getWrongAttempts() + 1);
                    if (u.getWrongAttempts() == 3){
                        u.setBlocked(true);
                        Storage.writeToFile("userList", Storage.allUsers);
                        message.setText("Too many attempts, user blocked");
                    }
                } break;
            } else {
                message.setText("User not found");
            }
        }
    }

    public void signUp(ActionEvent actionEvent) throws IOException {
        for (User u : Storage.allUsers){
            if (userName.getText().equals(u.getEmail())){
                message.setText("This email is already in use, log in");
                return;
            }
        }

        Storage.newEmail = userName.getText();
        Storage.newPassword = password.getText();
        Storage.last = "user";
        Transporter.openWindow((Stage) message.getScene().getWindow(), "add-user.fxml");
    }

    public void forgot(ActionEvent actionEvent) throws IOException {
        Transporter.openWindow((Stage) message.getScene().getWindow(), "restore-password.fxml");
    }

    public void sendEmail(ActionEvent actionEvent) {
        for (User u : Storage.allUsers){
            if (restoreEmail.getText().equals(u.getEmail())){
                u.setPassword("asdQWE123");
                restoreMessage.setText("New password sent to email");
                return;
            } else{
                restoreMessage.setText("User not found");
            }
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Transporter.openWindow((Stage) restoreMessage.getScene().getWindow(), "login.fxml");
    }
}
