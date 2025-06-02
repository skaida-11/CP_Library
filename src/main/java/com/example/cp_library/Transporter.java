package com.example.cp_library;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Transporter {
    public static void openWindow(Stage stage, String windowName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibrarySystem.class.getResource(windowName));
        var scene = new Scene(fxmlLoader.load(), 960, 720);
        stage.setScene(scene);
        stage.show();
    }

    public static boolean confirm(Stage stage, String author, String title, String buttonText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(stage);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setTitle("Confirmation");
        alert.setContentText(author + "\n\n" + title);

        ButtonType ok = new ButtonType(buttonText, ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(ok, cancel);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ok;
    }

}
