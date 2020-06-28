package client;

import client.controllers.ContrMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StartClient extends Application {
    private String themeDark = getClass().getResource("/client/css/Dark.css").toExternalForm();
    private String themeLight = getClass().getResource("/client/css/Light.css").toExternalForm();
    ContrMain controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("fxml/MainWindow.fxml"));
        controller = loader.getController();
        primaryStage.setTitle("Chat FXML & CSS");
        Rectangle2D sSize = Screen.getPrimary().getBounds();
        Scene scene = new Scene(root, sSize.getWidth()/2, sSize.getHeight()/2);
        scene.getStylesheets().add(themeDark);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            controller.disposed();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
