package VeryCoolGUI;

import Business.*;
import Exceptions.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;


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

    public void exitProgram(ActionEvent ae) {
        System.exit(1);
    }


    public void playMusic(ActionEvent actionEvent) {
        //searchByName now returns a list
        //mediacenter.searchByName(search.getText()).orElseThrow().play();
    }

    public void loginCheckCredentials(ActionEvent ae) throws IOException {
        String usr = email.getText();
        String passwd = password.getText();
        try {
            user = mediacenter.login(usr, passwd);
            if (user instanceof Administrador)
                swapFxml(ae, "resources/ourMediaAdmin.fxml");
            else
                swapFxml(ae, "resources/ourMedia.fxml");
        } catch (NonExistentUserException | InvalidPasswordException | AlreadyLoggedInException e) {
            email.setText("");
            password.setText("");
        } catch (NonSettedPasswdException e) {
            user = e.getUser();
            swapFxml(ae, "resources/createPassword.fxml");
        }
    }

    public void setPassword(ActionEvent ae) throws IOException {
        String password = this.password.getText();
        if (password != null){
            user.setPasswd(password);
            user = null;
            swapFxml(ae, "resources/login.fxml");
        }
    }

    public void editProfile(ActionEvent ae) {
        String e = email.getText();
        String n = name.getText();
        String p = password.getText();
        if (p != null)
            //TODO pls fix
            user.setPasswd(p);
    }

    public void uploadMedia(ActionEvent ae) throws IOException {
        String path = pathToFile.getText();
        String nome = mediaName.getText();
        String artist = mediaArtist.getText();
        String categoria = "k-pop";
        LocalDate date = datePicker.getValue();
        if (path != null) {
            // TODO pls fix
            // Music music = new Musica(user, path, nome, artist, categoria, date);
            mediacenter.uploadMedia(user, path);
            swapFxml(ae, "resources/myMedia.fxml");
        }
    }

    public void selectMusicUpload(ActionEvent ae) {
        Node but = (Node) ae.getSource();
        Stage stage = (Stage) but.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Select File for Upload");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Music Files", "*.mp3", "*.wav", "*.flac"));
        File selectedFile = fc.showOpenDialog(stage);
        pathToFile.setText(selectedFile.getPath());
    }

    public void selectVideoUpload(ActionEvent ae) {
        Node but = (Node) ae.getSource();
        Stage stage = (Stage) but.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("Select File for Upload");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Video Files", "*.mp4", "*.avi", "*.mkv"));
        File selectedFile = fc.showOpenDialog(stage);
        pathToFile.setText(selectedFile.getPath());
    }

    public void logout(ActionEvent ae) throws IOException {
        swapFxml(ae,"resources/login.fxml");
        user = null;
    }

    public void loginConvidado(ActionEvent ae) throws IOException {
        user = new Convidado();
        swapFxml(ae,"resources/ourMediaConvidado.fxml");
    }

    public void changeCriarConta(ActionEvent ae) throws IOException {
        swapFxml(ae,"resources/criarConta.fxml");
    }

    public void changeEditProfile(ActionEvent ae) throws IOException {
        swapFxml(ae,"resources/editProfile.fxml");
    }

    public void changeOurMedia(ActionEvent ae) throws IOException {
        swapFxml(ae,"resources/ourMedia.fxml");

    }

    public void changeOurMediaAdmin(ActionEvent ae) throws IOException {
        swapFxml(ae,"resources/ourMediaAdmin.fxml");
    }

    public void changeMyMedia(ActionEvent ae) throws IOException {
        swapFxml(ae,"resources/myMedia.fxml");
    }

    public void changeInicio(ActionEvent ae) throws IOException {
        swapFxml(ae,"resources/inicio.fxml");
    }

    public void changeLogin(ActionEvent ae) throws IOException {
        swapFxml(ae,"resources/login.fxml");
    }

    public void changeUploadMusic(ActionEvent ae) throws IOException {
        swapFxml(ae, "resources/uploadMusic.fxml");
    }

    public void changeUploadVideo(ActionEvent ae) throws IOException {
        swapFxml(ae, "resources/uploadVideo.fxml");
    }

    private void swapFxml(ActionEvent ae, String name) throws IOException {
        Node but = (Node) ae.getSource();
        Stage stage = (Stage) but.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(name));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void mouseHover(MouseEvent me) {
        Button b = (Button) me.getSource();
        b.setEffect(null);
        b.setStyle("-fx-background-color: #343438");
    }

    public void mouseUnhover(MouseEvent me) {
        Button b = (Button) me.getSource();
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
