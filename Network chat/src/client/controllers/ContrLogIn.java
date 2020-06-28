package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ContrLogIn {
    private ContrMain mainWindow;

    public void setParent (ContrMain main){
        mainWindow = main;
    }

    @FXML
    PasswordField passField;

    @FXML
    TextField loginField;

    @FXML
    Label authStatus;

    @FXML
    AnchorPane loginWindow;

    @FXML
    Button btnLogin;

    public void logInChat(){
        mainWindow.tryToAuth(loginField.getText(), passField.getText());
    }

    public void closeWindow(){
        passField.clear();
        loginField.clear();
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }

    public void setAuthStatus(String status){
        authStatus.setText(status);
    }

    public void toPassw(){
        passField.requestFocus();
    }
}
