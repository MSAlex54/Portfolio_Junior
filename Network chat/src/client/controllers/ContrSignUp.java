package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class ContrSignUp {
    @FXML
    TextField loginField;
    @FXML
    TextField nickNameField;
    @FXML
    PasswordField passField;
    @FXML
    PasswordField confField;
    @FXML
    Label chkPassResult;
    @FXML
    Label chkLoginResult;

    private ContrMain mainWindow;

    public void setParent (ContrMain main){
        mainWindow = main;
    }

    public void tryAndSignUp(){
        if (checkPass()){
            if (comparePass()){
                mainWindow.tryToSignUp(loginField.getText(), nickNameField.getText(), passField.getText());
            } else{
                chkPassResult.setText("Password and Confirm not equals");
            }
        } else {
            chkPassResult.setText("Password is too easy");
        }
    }
    public void setChkLoginResult(String text){
        chkLoginResult.setText(text);
    }

    public void closeWindow(){
        passField.clear();
        loginField.clear();
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.close();
    }
    public boolean checkPass(){
        Boolean result = false;
        String pass  = passField.getText();
        Pattern kirillPat = Pattern.compile("^.*[а-яА-Я].*$");
        Pattern mainPat = Pattern.compile("(?=^.{5,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$");//Check password
        result = mainPat.matcher(pass).matches()& !kirillPat.matcher(pass).matches();
        return result ;
    }

    public boolean comparePass(){
        return passField.getText().equals(confField.getText());
    }

}
