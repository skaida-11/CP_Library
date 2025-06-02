package com.example.cp_library;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserEdit implements Initializable {

    public TextField editEmail;
    public TextField editPassword;
    public TextField editName;
    public Button editButton;
    public Label editMessage;
    public Label id;
    public ChoiceBox blocked;
    public TextField passwordAgain;
    public String mailStorage;
    public Label newPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!Storage.last.equals("user")){
        blocked.getItems().addAll(Boolean.TRUE, Boolean.FALSE);}

        if (Storage.selectedUser != null) {
            editEmail.setText(Storage.selectedUser.getEmail());
            editName.setText(Storage.selectedUser.getName());
            id.setText(Storage.selectedUser.getId());
            mailStorage = Storage.selectedUser.getEmail();
            if (!Storage.last.equals("user")){
                blocked.setValue(Storage.selectedUser.isBlocked());
            }
        }
    }

    public void editUserU(ActionEvent actionEvent) {
        String name = editName.getText().trim();
        String email = editEmail.getText().trim();
        String pass1 = editPassword.getText().trim();
        String pass2 = passwordAgain.getText().trim();

        if(name.isEmpty()| email.isEmpty()|| pass1.isEmpty()|| pass2.isEmpty()){
            editMessage.setText("Please fill all fields");
            return;
        }
        if (!Verification.emailUnique(email) && !email.equals(mailStorage)){
            editMessage.setText("This email is unavailable");
            return;
        }
        if (!Verification.emailFormat(email)){
            editMessage.setText("Please check email format");
            return;
        }
        if (!Verification.strongPassword(pass1)){
            editMessage.setText("Password is not strong enough");
            return;
        }
        if (!Verification.correctPassword(pass1, pass2)){
            editMessage.setText("Password do not match");
            return;
        }

        for (User u : Storage.allUsers){
            if (u.getId().equals(id)){
                u.setEmail(email);
                u.setName(name);
                u.setPassword(pass1);
                break;
            }
        }
        Storage.writeToFile("userList", Storage.allUsers);
        editMessage.setText("User edit successful");

    }

    public void editUserA(ActionEvent actionEvent) {
        String name = editName.getText().trim();
        String email = editEmail.getText().trim();
        Boolean isBlocked = (Boolean) blocked.getValue();

        if(name.isEmpty()|| email.isEmpty()){
            editMessage.setText("Please fill all fields");
            return;
        }
        if (!Verification.emailUnique(email) && !email.equals(mailStorage)){
            editMessage.setText("This email is unavailable");
            return;
        }
        if (!Verification.emailFormat(email)){
            editMessage.setText("Please check email format");
            return;
        }

        for (User u : Storage.allUsers){
            if (u.getId().equals(id.getText())){
                u.setEmail(email);
                u.setName(name);
                u.setPassword(id.getText().concat(name));
                u.setBlocked(isBlocked);
                u.setWrongAttempts(0);
                break;
            }
        }
        Storage.writeToFile("userList", Storage.allUsers);
        newPassword.setText(id.getText().concat(name));
        editMessage.setText("User edit successful");
    }

    public void backButton(ActionEvent actionEvent) throws IOException {

        if (Storage.last.equals("admin")) {
            Transporter.openWindow((Stage) editMessage.getScene().getWindow(), "admin-user.fxml");
        } else {
            Transporter.openWindow((Stage) editMessage.getScene().getWindow(), "user.fxml");
        }
    }

}
