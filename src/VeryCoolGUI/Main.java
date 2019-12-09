package VeryCoolGUI;

import Exceptions.*;
import business.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Main extends Application {

    private static MediaCenter mediacenter;
    private  static Utilizador user;

    /* button */
    public Button exit, back;
    public Button confirm, select;
    public Button login, logout;
    public Button play, pause;
    public Button upload, download;
    public Button criarBiblioteca;
    public Button myMedia;
    public Button friends;
    public Button editProfile;
    public Button createAcount;

    /* user input fields */
    public TextField search;
    public TextField name, email, password;
    public DatePicker datePicker;
    public SplitMenuButton dropDownMenu;

    /* data display */
    public Label pathToFile;
    public TableView<Musica> mediaTable;
    public TableColumn<Musica, String> colName;
    public TableColumn<Musica, String> colCategoria;
    public TableColumn<Musica, Integer> colDuration;

    /* myMedia.fxml*/
    public Button myMediaRemove;
    public Button myMediaClassificar;
    
    /* upload media*/
    public TextField mediaName;
    public TextField mediaArtist;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        mediacenter = new MediaCenter();
        Administrador admin = new Administrador("admin", "admin");
        Utilizador test;
        try {
            test = mediacenter.createUser(admin, "adeus", "ola");
            test.setPasswd("123");
            test = mediacenter.createUser(admin, "help", "pls");

            mediacenter.uploadMedia(test, "/home/mightymime/Music/Xexe_Band-Afoga_o_Ganso.mp3");
        } catch (PermissionDeniedException | UserExistsException ignored) {}

        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("resources/inicio.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setTitle("VeryCoolGUI.jpeg");
        stage.show();
    }

    public void exitProgram(ActionEvent actionEvent) {
        System.exit(1);
    }

    public void playMusic(ActionEvent actionEvent) {
        mediacenter.searchByName(search.getText()).orElseThrow().play();
    }

    public void loginCheckCredentials(ActionEvent actionEvent) throws IOException {
        String usr = email.getText();
        String passwd = password.getText();
        try {
            user = mediacenter.login(usr, passwd);
            swapFxml(actionEvent, "resources/ourMedia.fxml");
        } catch (NonExistentUserException | InvalidPasswordException | AlreadyLoggedInException e) {
            email.setText("");
            password.setText("");
        } catch (NonSettedPasswdException e) {
            swapFxml(actionEvent, "resources/createPassword.fxml");
        }
    }

    public void setPassword(ActionEvent actionEvent) throws IOException {
        String password = this.password.getText();
        if (password != null){
            user.setPasswd(password);
            user = null;
            swapFxml(actionEvent, "resources/login.fxml");
        }
    }

    public void editProfile(ActionEvent actionEvent) {
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        swapFxml(actionEvent,"resources/login.fxml");
        user = null;
    }

    public void loginConvidado(ActionEvent actionEvent) throws IOException {
        user = new Convidado();
        swapFxml(actionEvent,"resources/ourMediaConvidado.fxml");
    }

    public void changeCriarConta(ActionEvent actionEvent) throws IOException {
        swapFxml(actionEvent,"resources/criarConta.fxml");
    }

    public void changeEditProfile(ActionEvent actionEvent) throws IOException {
        swapFxml(actionEvent,"resources/editProfile.fxml");
    }

    public void changeOurMedia(ActionEvent actionEvent) throws IOException {
        swapFxml(actionEvent,"resources/ourMedia.fxml");

    }

    public void changeOurMediaAdmin(ActionEvent actionEvent) throws IOException {
        swapFxml(actionEvent,"resources/ourMediaAdmin.fxml");
    }

    public void changeMyMedia(ActionEvent actionEvent) throws IOException {
        swapFxml(actionEvent,"resources/myMedia.fxml");
    }

    public void changeInicio(ActionEvent actionEvent) throws IOException {
        swapFxml(actionEvent,"resources/inicio.fxml");
    }

    public void changeLogin(ActionEvent actionEvent) throws IOException {
        swapFxml(actionEvent,"resources/login.fxml");
    }

    public void changeUploadMedia(ActionEvent actionEvent) throws IOException {
        swapFxml(actionEvent, "resources/uploadMedia.fxml");
    }

    public void uploadMedia(ActionEvent actionEvent) throws IOException {
        String path = pathToFile.getText();
        if (path != null) {
            mediacenter.uploadMedia(user, path);
            swapFxml(actionEvent, "resources/myMedia.fxml");
        }
    }

    public void selectFileUpload(ActionEvent actionEvent) {
        Node but = (Node) actionEvent.getSource();
        Stage stage = (Stage) but.getScene().getWindow();

        FileChooser fc = new FileChooser();
        fc.setTitle("Select File for Upload");

        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Media Files", "*.mp3", "*.mp4", "*.wav", "*.flac", "*.avi"));

        File selectedFile = fc.showOpenDialog(stage);

        pathToFile.setText(selectedFile.getPath());
    }

    private void swapFxml(ActionEvent actionEvent, String name) throws IOException {
        Node but = (Node) actionEvent.getSource();
        Stage stage = (Stage) but.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(name));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void mouseHover(MouseEvent mouseEvent) {
        Button b = (Button) mouseEvent.getSource();
        b.setEffect(null);
        b.setStyle("-fx-background-color: #343438");
    }

    public void mouseUnhover(MouseEvent mouseEvent) {
        Button b = (Button) mouseEvent.getSource();
        b.setStyle("-fx-background-color: #2b2b2f");
        DropShadow s = new DropShadow();
        s.setWidth(0);
        s.setHeight(1);
        s.setRadius(0);
        s.setOffsetX(0);
        s.setOffsetY(2);
        s.setSpread(0);
        b.setEffect(s);
    }
}
