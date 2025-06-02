package com.example.cp_library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserAdmin implements Initializable {
    public TableView userTable;
    public ObservableList<User> users;
    public Button backButton;
    public Label message;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        users = FXCollections.observableList(Storage.allUsers);
        userTable.setItems(users);
    }
    public void moveToAddUser(ActionEvent actionEvent) throws IOException {
        Storage.last = "admin";
        Transporter.openWindow((Stage) backButton.getScene().getWindow(), "add-user.fxml");
    }

    public void moveToEditUser(ActionEvent actionEvent) throws IOException {
        Storage.last = "admin";
        Storage.selectedUser = (User) userTable.getSelectionModel().getSelectedItem();
        if (Storage.selectedUser == null) {
            message.setText("Please select user to edit");
        } else {
            Transporter.openWindow((Stage) backButton.getScene().getWindow(), "edit-userA.fxml");
        }
    }
    public void moveToDeleteUser(ActionEvent actionEvent) {
        Storage.selectedUser = (User) userTable.getSelectionModel().getSelectedItem();
        for (User u : Storage.allUsers){
            if (Storage.selectedUser.getEmail().equals(u.getEmail())){
                Storage.allUsers.remove(u);
                Storage.writeToFile("userList", Storage.allUsers);
                message.setText("User deleted");
            }
        }
    }

    public void backToAdmin(ActionEvent actionEvent) throws IOException {
        Transporter.openWindow((Stage) backButton.getScene().getWindow(), "admin.fxml");
    }


}
