package VeryCoolGUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;


public class Main extends Application {

    public Button backLogin;
    public Button inicioSair;
    public Button inicioLogin;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("login.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setTitle("VeryCoolGUI.jpeg");
        stage.show();
    }


    public void exitProgram(ActionEvent actionEvent) {
        System.exit(1);
    }

    public void changeMenu(ActionEvent actionEvent) throws IOException {
        Parent root = null;
        Stage stage = null;
        if (actionEvent.getSource()==backLogin){
            stage = (Stage) backLogin.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("inicio.fxml"));
        }
        if (actionEvent.getSource()==inicioLogin){
            stage = (Stage) inicioLogin.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("login.fxml"));
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
