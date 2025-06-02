package com.example.cp_library;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserAdd implements Initializable {
    public Label id;
    public TextField name;
    public TextField email;
    public Button backButton;
    public Label addMessage;
    public TextField password;
    public TextField passwordAgain;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String singUpEmail = Storage.newEmail;
        String singUpPass = Storage.newPassword;
        id.setText("X" + (501 + Storage.allUsers.size()));
        if (singUpEmail != null){
            email.setText(singUpEmail);
        }
        if (singUpPass != null){
            password.setText(singUpPass);
        }
    }

    public void addNewUser(ActionEvent actionEvent) {
        String uName = name.getText().trim();
        String uEmail = email.getText().trim();
        String pass1 = password.getText().trim();
        String pass2 = passwordAgain.getText().trim();

        if (uName.isEmpty() || uEmail.isEmpty()|| pass1.isEmpty() || pass2.isEmpty()){
            addMessage.setText("Please fill all fields");
            return;
        }

        if (!Verification.emailFormat(uEmail)){
            addMessage.setText("Please check your email format");
            return;
        }

        if (!Verification.emailUnique(uEmail)){
            addMessage.setText("This email is already in use");
            return;
        }

        if (!Verification.strongPassword(pass1)){
            addMessage.setText("Password must be at least 8 characters long, contain uppercase and lowercase letter and a digit!");
            return;
        }

        if (!Verification.correctPassword(pass1, pass2)){
            addMessage.setText("Passwords do not match");
            return;
        }

        User newUser = new User(id.getText(), pass1, uName, uEmail);
        Storage.allUsers.add(newUser);
        addMessage.setText("New user added");
        Storage.writeToFile("userList", Storage.allUsers);

    }

    public void backToUser(ActionEvent actionEvent) throws IOException {
        if (Storage.last.equals("admin")) {
            Transporter.openWindow((Stage) addMessage.getScene().getWindow(), "admin-user.fxml");
        } else {
            Transporter.openWindow((Stage) addMessage.getScene().getWindow(), "login.fxml");
        }
    }


}
