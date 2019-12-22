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
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Main extends Application {

    private static MediaCenter mediacenter;
    private static Stage stage;

    /* button */
    @FXML
    private Button exit, back, swap;
    @FXML
    private Button create, remove;
    @FXML
    private Button confirm, select;
    @FXML
    private Button login, logout;
    @FXML
    private Button playPause, stop;
    @FXML
    private Button upload, download;
    @FXML
    private Button criarBiblioteca;
    @FXML
    private Button myMedia;
    @FXML
    private Button friends;
    @FXML
    private Button editProfile;
    @FXML
    private Button editAcount;
    @FXML
    private Button myMediaRemove;
    @FXML
    private Button myMediaClassificar;

    /* user input fields */
    @FXML
    private TextField search;
    @FXML
    private TextField name, email, password, oldPasswd;
    @FXML
    private TextField mediaName;
    @FXML
    private TextField musicAlbum, musicTrack, musicSinger;
    @FXML
    private TextField videoSerie, videoSeason, videoEpisode;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox dropDownMenu;
    @FXML
    private ChoiceBox searchBy;

    /* data display */
    @FXML
    private Label pathToFile;
    @FXML
    private ListView<String> listViewMedia;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage fStage) throws Exception {
        mediacenter = new MediaCenter();
        stage = fStage;
        Parent root = FXMLLoader.load(getClass().getResource("resources/inicio.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("VeryCoolGUI.jpeg");
        stage.show();
    }

    //Events
    public void populateListOnTyping(KeyEvent keyEvent) {
        populateList(getOurMediaDisplay());
    }

    public void populateTableOnClick(ActionEvent event) {
        populateList(getOurMediaDisplay());
    }
    //Upload Media
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
            if((episode != null && episode <= 0) || (season != null && season <= 0))
                throw new NumberFormatException();

            if (path != null && !nome.equals("") && date != null) {
                Video video = new Video(
                        mediacenter.getEmail(),
                        path,
                        nome,
                        serie.equals("") ? null : serie,
                        season,
                        episode,
                        Date.valueOf(date));

                mediacenter.uploadMedia(video);
                changeMyMedia(ae);
            }
        } catch (NumberFormatException e) {
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
        } catch (NumberFormatException e) {
            musicTrack.setText("");
        }

        if (path != null && !name.equals("") && !album.equals("") && !singer.equals("") &&
                date != null && categoria != null && faixa != null) {
            try {
                Musica music = new Musica(
                        name, path, mediacenter.getEmail(), album, singer, faixa, Date.valueOf(date), categoria);

                mediacenter.uploadMedia(music);
                changeMyMedia(ae);
            } catch (InvalidMusicException e) {
            } catch (InvalidGenreException ignored) {
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
        } catch (NullPointerException ignored) {
        }
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
        try {
            pathToFile.setText(selectedFile.getPath());

            Metadata metadata = new Metadata(selectedFile.getPath());

            mediaName.setText(metadata.getNome());
            datePicker.setValue(metadata.getData());
        } catch (NullPointerException ignored) {
        }
    }

    //Controll Media
    public void playAllMusic(ActionEvent ae) {
        mediacenter.playMedia(getOurMediaDisplay());
    }

    public void playMusicClick(MouseEvent me) {
        if (me.getButton() == MouseButton.PRIMARY && me.getClickCount() == 2) {
            int pos = listViewMedia.getSelectionModel().getSelectedIndex();
            if (pos >= 0)
                mediacenter.playMedia(getOurMediaDisplay().get(pos));
        }
    }

    //Edit Users
    public void setPassword(ActionEvent ae) throws IOException, SettedPasswdException {
        String password = this.password.getText();
        if (!password.equals("")) {
            mediacenter.fstPasswd(password);
            changeLogin(ae);
            mediacenter.logout();
        }
    }

    public void editProfile(ActionEvent ae) throws IOException {
        String n = name.getText();
        String p = password.getText();
        String op = oldPasswd.getText();
        try {
            if (n != null)
                mediacenter.chName(n);
            if (p != null && op != null)
                mediacenter.passwd(op, p);
            changeOurMedia(ae);
        } catch (InvalidPasswordException | NonSettedPasswdException ignored) {
            name.setText("");
            password.setText("");
            oldPasswd.setText("");
        }
    }

    public void removeUser(ActionEvent ae) throws IOException {
        String e = email.getText();
        if (e != null) {
            try {
                mediacenter.rmUser(e);
                changeOurMedia(ae);
            } catch (PermissionDeniedException ignored) {
                changeOurMedia(ae);
            }
        }
    }

    public void createUser(ActionEvent ae) throws IOException {
        String e = email.getText();
        String n = name.getText();
        if (e != null && n != null) {
            try {
                mediacenter.createUser(e, n);
                changeOurMedia(ae);
            } catch (UserExistsException ignored) {
                email.setText("");
                name.setText("");
            } catch (PermissionDeniedException ignored) {
                changeOurMedia(ae);
            }
        }
    }

    //Change Menu
    public void loginCheckCredentials(ActionEvent ae) throws IOException {
        String usr = email.getText();
        String passwd = password.getText();
        try {
            mediacenter.login(usr, passwd);
            changeOurMedia(ae);
        } catch (NonExistentUserException | InvalidPasswordException | AlreadyLoggedInException e) {
            email.setText("");
            password.setText("");
        } catch (NonSettedPasswdException e) {
            swapFxml(ae, "resources/createPassword.fxml");
        }
    }

    public void changeOurMedia(ActionEvent ae) throws IOException {
        if (mediacenter.isAdmin())
            swapFxml(ae, "resources/ourMediaAdmin.fxml");
        else
            swapFxml(ae, "resources/ourMedia.fxml");
    }

    public void changeMyMedia(ActionEvent ae) throws IOException {
        swapFxml(ae, "resources/myMedia.fxml");
    }

    public void changeInicio(ActionEvent ae) throws IOException {
        swapFxml(ae, "resources/inicio.fxml");
    }

    public void logout(ActionEvent ae) throws IOException {
        swapFxml(ae, "resources/login.fxml");
        mediacenter.logout();
    }

    public void exitProgram(ActionEvent ae) {
        System.exit(1);
    }

    public void loginConvidado(ActionEvent ae) throws IOException {
        swapFxml(ae, "resources/ourMediaConvidado.fxml");
    }

    public void changeCriarConta(ActionEvent ae) throws IOException {
        swapFxml(ae, "resources/criarConta.fxml");
    }

    public void changeEditProfile(ActionEvent ae) throws IOException {
        swapFxml(ae, "resources/editProfile.fxml");
    }

    public void changeLogin(ActionEvent ae) throws IOException {
        swapFxml(ae, "resources/login.fxml");
    }

    public void changeUploadMusic(ActionEvent ae) throws IOException {
        swapFxml(ae, "resources/uploadMusic.fxml");
    }

    public void changeUploadVideo(ActionEvent ae) throws IOException {
        swapFxml(ae, "resources/uploadVideo.fxml");
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

    // auxiliary
    private void swapFxml(ActionEvent ae, String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public List<Media> getOurMediaDisplay() {
        String q = search.getText();
        if (q.equals(""))
            return new ArrayList<>(mediacenter.allMedia());
        else {
            switch (searchBy.getValue().toString()) {
                case "ARTISTA":
                    return mediacenter.searchByArtist(q);
                case "CATEGORIA":
                    return mediacenter.searchByCat(q);
            }
            return mediacenter.searchByName(q);
        }
    }

    public void populateList(List<Media> m) {
        listViewMedia.setItems(FXCollections.observableList(
                m.stream().map(Media::toString).collect(Collectors.toList())
                                                           ));
    }
}
