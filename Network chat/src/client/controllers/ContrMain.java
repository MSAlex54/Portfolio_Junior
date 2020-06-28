package client.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ContrMain {
    @FXML
    TextArea chatArea;
    @FXML
    VBox vBoxChat;
    @FXML
    ScrollPane scrolChat;
    @FXML
    TextField msgField;
    @FXML
    VBox mainWindow;
    @FXML
    GridPane mainPanel;
    @FXML
    ListView<String> userList;
    @FXML
    HBox loginPanel;
    @FXML
    HBox SignupPanel;
    @FXML
    ContextMenu cmUserList;
    private String themeDark = getClass().getResource("/client/css/Dark.css").toExternalForm();
    private String themeLight = getClass().getResource("/client/css/Light.css").toExternalForm();
    private Scene curScene;

    private boolean isAuthorized;
    private ContrLogIn logInC;
    private Stage localStage;
    private ContrSignUp signUpC;
    private ContrPM contrPM;
    private ContrOption contrOption;

    public void setAuthorized(boolean isAuthorized){
        this.isAuthorized = isAuthorized;
        if (!isAuthorized){
            loginPanel.setVisible(true);
            loginPanel.setManaged(true);
            SignupPanel.setManaged(true);
            SignupPanel.setManaged(true);
            mainPanel.setVisible(false);
            mainPanel.setManaged(false);
        } else {
            loginPanel.setVisible(false);
            loginPanel.setManaged(false);
            SignupPanel.setVisible(false);
            SignupPanel.setManaged(false);
            mainPanel.setVisible(true);
            mainPanel.setManaged(true);
        }
    }

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRESS = "localhost";
    final int PORT = 8189;

    public void connect() {
        try {
            socket = new Socket(IP_ADRESS,PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/")){
                                if(str.startsWith("/auth")){
                                    if(str.startsWith("/authok")) {
                                        setAuthorized(true);
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                logInC.closeWindow();
                                            }
                                        });
                                        break;
                                    } else {
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                logInC.setAuthStatus(str.split(" ",2)[1]);
                                            }
                                        });
                                    }
                                }
                                if(str.startsWith("/signup")){
                                    if(str.startsWith("/signupok")) {
                                        setAuthorized(true);
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                signUpC.closeWindow();
                                            }
                                        });
                                        break;
                                    } else{
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                signUpC.setChkLoginResult(str.split(" ",2)[1]);
                                            }
                                        });
                                    }
                                }
                            }
                        }
                        //
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/")) {
                                if (str.equals("/serverClosed")) break;
                                if (str.startsWith("/clientlist")) {
                                    String[] tokens = str.split(" ");
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            userList.getItems().clear();
                                            for (int i = 1; i < tokens.length; i++) {
                                                userList.getItems().add(tokens[i]);
                                            }
                                        }
                                    });
                                }  else if(str.startsWith("/update")){
                                    if(str.startsWith("/updateok")) {
                                        contrOption.setUpdateStatus("Done");
                                    } else{
                                        contrOption.setUpdateStatus("Nick is busy");
                                    }
                                } else {
                                    str = str.substring(1);
                                    displayMsg(str);
                                }
                            }else{
                                displayMsg(str);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        setAuthorized(false);
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(){
        if (!msgField.getText().equals("")) {
            try {
                out.writeUTF(msgField.getText());
                msgField.clear();
                msgField.requestFocus();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMsg(String msg){
        if (!msg.equals("")) {
            try {
                out.writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void addToBL(){
        if(userList.getSelectionModel().getSelectedItem()!=null) {
            try {
                out.writeUTF("/blackadd " + userList.getSelectionModel().getSelectedItem());
                cmUserList.hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void removeFromToBL(){
        if(userList.getSelectionModel().getSelectedItem()!=null) {
            try {
                out.writeUTF("/blackdel " + userList.getSelectionModel().getSelectedItem());
                cmUserList.hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void exitProgram(){
        disposed();
    }

    public void tryToAuth(String login, String pass) {
        if(socket==null||socket.isClosed()){
            connect();
        }
        try {
            out.writeUTF("/auth " + login + " "  + pass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToSignUp(String login,String nick, String pass){
        if (socket==null||socket.isClosed()){
            connect();
        }
        try {
            out.writeUTF("/signup " + login + " "  + nick + " "  + pass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disposed(){
        System.out.println("Closing cient");
        try {
            if (out!=null) {
                out.writeUTF("/end");
                Platform.exit();
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyNickName(){
        if(userList.getSelectionModel().getSelectedItem()!=null) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(userList.getSelectionModel().getSelectedItem());
            clipboard.setContent(content);
        } else  {
            cmUserList.hide();
        }
    }

    public void sendPM()throws IOException {
        if(userList.getSelectionModel().getSelectedItem()!=null) {
            localStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/fxml/PMWindow.fxml"));
            Parent loginParent = (Parent) loader.load();
            localStage.setTitle(userList.getSelectionModel().getSelectedItem());
            localStage.setScene(new Scene(loginParent, 350, 50));
            localStage.show();
            contrPM = loader.getController();
            contrPM.setParent(this);
        } else  {
            cmUserList.hide();
        }
    }
    @FXML
    public void showLogin() throws IOException {
        localStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/fxml/LogInWindow.fxml"));
        Parent loginParent = (Parent) loader.load();
        localStage.setTitle("Login");
        localStage.setScene(new Scene(loginParent, 300, 150));
        localStage.show();
        logInC = loader.getController();
        logInC.setParent(this);
    }
    @FXML
    public void showSignUp() throws IOException {
        localStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/fxml/SignUpWindow.fxml"));
        Parent loginParent = (Parent) loader.load();
        localStage.setTitle("SignUp");
        localStage.setScene(new Scene(loginParent, 550, 250));
        localStage.show();
        signUpC = loader.getController();
        signUpC.setParent(this);
    }
    @FXML
    public void showOptions() throws IOException {
        localStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/fxml/OptionWindow.fxml"));
        Parent parent = (Parent) loader.load();
        localStage.setTitle("Options");
        localStage.setScene(new Scene(parent, 500, 300));
        localStage.show();
        contrOption = loader.getController();
        contrOption.setParent(this);
    }

    public void setDarkTheme(){
        curScene = mainWindow.getScene();
        curScene.getStylesheets().remove(themeLight);
        if(!curScene.getStylesheets().contains(themeDark)) curScene.getStylesheets().add(themeDark);
    }
    public void setLightTheme(){
        curScene = mainWindow.getScene();
        curScene.getStylesheets().remove(themeDark);
        if(!curScene.getStylesheets().contains(themeLight)) curScene.getStylesheets().add(themeLight);
    }

    private void displayMsg(String str){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label label = new Label(str + "\n");
                VBox vBox = new VBox();
                if (str.startsWith("I:" + "\n")){
                    vBox.setAlignment(Pos.TOP_RIGHT);
                } else{
                    vBox.setAlignment(Pos.TOP_LEFT);
                }
                vBox.getChildren().add(label);
                vBoxChat.getChildren().add(vBox);
                vBoxChat.setPrefHeight(scrolChat.getHeight()-4);
                vBoxChat.setPrefWidth(scrolChat.getWidth()-4);
            }
        });

    }
}
