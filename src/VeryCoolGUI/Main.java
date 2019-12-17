package VeryCoolGUI;

import Business.*;
import Exceptions.*;
import Utils.Metadata;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class Main extends Application {

    private static MediaCenter mediacenter;
    private  static Utilizador user;
    private static Stage stage;


    public ListView<String> listViewMedia;


    /* button */
    @FXML private Button exit, back, swap;
    @FXML private Button create, remove;
    @FXML private Button confirm, select;
    @FXML private Button login, logout;
    @FXML private Button playPause, stop;
    @FXML private Button upload, download;
    @FXML private Button criarBiblioteca;
    @FXML private Button myMedia;
    @FXML private Button friends;
    @FXML private Button editProfile;
    @FXML private Button editAcount;
    @FXML private Button myMediaRemove;
    @FXML private Button myMediaClassificar;

    /* user input fields */
    @FXML private TextField search;
    @FXML private TextField name, email, password, oldPasswd;
    @FXML private TextField mediaName;
    @FXML private TextField musicAlbum, musicTrack, musicSinger;
    @FXML private TextField videoSerie, videoSeason, videoEpisode;
    @FXML private DatePicker datePicker;
    @FXML private ChoiceBox dropDownMenu;

    /* data display */
    @FXML private Label pathToFile;

    @FXML private Label displayMediaTMP;

    @FXML private TableView<Media> mediaTable;
    @FXML private TableColumn<Media, String> nameCol;
    @FXML private TableColumn<Media, String> pathCol;
    @FXML private TableColumn<Media, String> ownerCol;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage fStage) throws Exception{
        mediacenter = new MediaCenter();
        stage = fStage;
        Parent root = FXMLLoader.load(getClass().getResource("resources/inicio.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("VeryCoolGUI.jpeg");
        stage.show();
    }

    public void exitProgram(ActionEvent ae) {
        System.exit(1);
    }
    
    public void playMusic(ActionEvent ae) {
        mediacenter.searchByName(search.getText()).get(0).play();
    }

    public void loginCheckCredentials(ActionEvent ae) throws IOException {
        String usr = email.getText();
        String passwd = password.getText();
        try {
            user = mediacenter.login(usr, passwd);
            changeOurMedia(ae);
        } catch (NonExistentUserException | InvalidPasswordException | AlreadyLoggedInException e) {
            email.setText("");
            password.setText("");
        } catch (NonSettedPasswdException e) {
            user = e.getUser();
            swapFxml(ae, "resources/createPassword.fxml");
        }
    }

    public void setPassword(ActionEvent ae) throws IOException, SettedPasswdException {
        String password = this.password.getText();
        if (password != null){
            mediacenter.fstPasswd(user, password);
            changeLogin(ae);
            user = null;
        }
    }

    public void editProfile(ActionEvent ae) throws IOException {
        String n = name.getText();
        String p = password.getText();
        String op = oldPasswd.getText();
        try {
            if (n != null)
                mediacenter.chName(user, n);
            if (p != null && op != null)
                mediacenter.passwd(user, op, p);
            changeOurMedia(ae);
        }
        catch (InvalidPasswordException | NonSettedPasswdException ignored){
            name.setText("");
            password.setText("");
            oldPasswd.setText("");
        }
    }

    public void removeUser(ActionEvent ae) throws IOException {
        String e = email.getText();
        if(e != null) {
            try {
                mediacenter.rmUser(user, e);
                System.out.println("Deleted");
                changeOurMedia(ae);
            }
            catch (PermissionDeniedException ignored){
                changeOurMedia(ae);
                System.out.println("Failed");
            }
        }
    }

    public void createUser(ActionEvent ae) throws IOException {
        String e = email.getText();
        String n = name.getText();
        if(e != null && n != null) {
            try {
                mediacenter.createUser(user, e, n);
                changeOurMedia(ae);
            } catch (UserExistsException ignored) {
                email.setText("");
                name.setText("");
            }
            catch (PermissionDeniedException ignored){
                changeOurMedia(ae);
            }
        }
    }

    public void uploadVideo(ActionEvent ae) throws IOException {
        String path = pathToFile.getText();
        String nome = mediaName.getText();
        String serie = videoSerie.getText();
        String sSeason = videoSeason.getText();
        String sEpisode = videoEpisode.getText();
        LocalDate date = datePicker.getValue();

        try {
            Integer episode = (sEpisode.equals(""))? null : Integer.parseInt(sEpisode);
            Integer season = (sSeason.equals(""))? null : Integer.parseInt(sEpisode);
            if (path != null && !nome.equals("") && date != null) {
                Video video = new Video(
                        user,
                        path,
                        nome,
                        serie.equals("")? null : serie,
                        season,
                        episode,
                        Date.valueOf(date));

                mediacenter.uploadMedia(user, video);
                changeMyMedia(ae);
            }
        }
        catch (NumberFormatException e){
            videoEpisode.setText("");
            videoSeason.setText("");
        }
    }

    public void uploadMusic(ActionEvent ae) throws IOException {
        String name = mediaName.getText();
        String path = pathToFile.getText();
        String album = musicAlbum.getText();
        String singer = musicSinger.getText();
        String sFaixa = musicTrack.getText();
        LocalDate date = datePicker.getValue();

        String categoria = (String) dropDownMenu.getValue();

        Integer faixa = null;
        try {
            faixa = Integer.parseInt(sFaixa);
        }
        catch (NumberFormatException e){
            musicTrack.setText("");
        }

        if (path != null && !name.equals("") && !album.equals("") && !singer.equals("") &&
                date != null && categoria != null && faixa != null) {
            try {
                Musica music = new Musica(name, path, user, album, singer, faixa, Date.valueOf(date), categoria);

                mediacenter.uploadMedia(user, music);
                changeMyMedia(ae);
            }
            catch (InvalidMusicException e) {}
            catch (InvalidGenreException ignored) {
                dropDownMenu.setValue(null);
            }
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
        try {
            pathToFile.setText(selectedFile.getPath());

            dropDownMenu.getItems().addAll(new Categoria().getAllGenres().stream().sorted().collect(Collectors.toList()));

            Metadata metadata = new Metadata(selectedFile.getPath());

            mediaName.setText(metadata.getNome());
            musicSinger.setText(metadata.getAuthor());
            musicAlbum.setText(metadata.getAlbum());
            musicTrack.setText(metadata.getFaixa());
            datePicker.setValue(metadata.getData());
            String cat = metadata.getCategoria();
            if (cat != null)
                dropDownMenu.setValue(cat);
        }
        catch(NullPointerException ignored){}
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
        try{
            pathToFile.setText(selectedFile.getPath());

            Metadata metadata = new Metadata(selectedFile.getPath());

            mediaName.setText(metadata.getNome());
            datePicker.setValue(metadata.getData());
        }
        catch(NullPointerException ignored){}
    }

    public void logout(ActionEvent ae) throws IOException {
        swapFxml(ae,"resources/login.fxml");
        user = null;
    }

    public void changeCriarConta(ActionEvent ae) throws IOException {
        swapFxml(ae,"resources/criarConta.fxml");
    }

    public void changeEditProfile(ActionEvent ae) throws IOException {
        swapFxml(ae,"resources/editProfile.fxml");
    }

    public void loginConvidado(ActionEvent ae) throws IOException {
        user = new Convidado();
        swapFxml(ae,"resources/ourMediaConvidado.fxml");
        
        //populateTable(mediacenter.searchByName(""));
    }

    public void changeOurMedia(ActionEvent ae) throws IOException {
        if(user.isAdmin())
            swapFxml(ae, "resources/ourMediaAdmin.fxml");
        else
            swapFxml(ae, "resources/ourMedia.fxml");

        //populateTable(mediacenter.searchByName(""));
    }

    public void populateTableOnTyping(KeyEvent keyEvent) {
        listViewMedia.setItems(FXCollections.observableList(
                mediacenter.searchByName(search.getText())
                        .stream()
                        .map(Media::toString)
                        .collect(Collectors.toList())
        ));
    }


    public void populateTable(List<Media> m){
        pathCol.setCellValueFactory(new PropertyValueFactory<>("path"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        ownerCol.setCellValueFactory(new PropertyValueFactory<>("owner"));

        mediaTable.getColumns().add(pathCol);
        mediaTable.getColumns().add(nameCol);
        mediaTable.getColumns().add(ownerCol);

        mediaTable.getItems().addAll(m);
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
        Parent root = FXMLLoader.load(getClass().getResource(name));
        stage.setScene(new Scene(root));
        stage.show();
    }

    // animations
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
