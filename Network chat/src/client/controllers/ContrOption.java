package client.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import server.AuthServer;
import server.ClientHandler;

import java.io.IOException;

public class ContrOption {
    private ContrMain mainWindow;

    public void setParent (ContrMain main){
        mainWindow = main;
    }
    @FXML
    ComboBox themeChooser;
    @FXML
    TextField newNick;
    @FXML
    Label updateStatus;

    public void changeTheme(){
        switch (themeChooser.getSelectionModel().getSelectedItem().toString()){
            case ("Dark"):
                mainWindow.setDarkTheme();
                break;
            case ("Light"):
                mainWindow.setLightTheme();
                break;
        }
    }

    public void updateNickName(){
        try {
            mainWindow.out.writeUTF("/update " + newNick.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setUpdateStatus(String status){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                updateStatus.setText(status);
            }
        });
    }

}
