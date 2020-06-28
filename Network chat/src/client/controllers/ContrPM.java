package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ContrPM {
    @FXML
    TextField msField;

    private ContrMain mainWindow;

    public void setParent (ContrMain main){
        mainWindow = main;
    }

    @FXML
    public void sendPM(){
        Stage stage = (Stage) msField.getScene().getWindow();
        mainWindow.sendMsg("/w " + stage.getTitle() + " " + msField.getText());
    }

}
