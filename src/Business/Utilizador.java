package Business;

import Exceptions.AlreadyLoggedInException;
import Exceptions.InvalidPasswordException;
import Exceptions.NonSettedPasswdException;
import Exceptions.SettedPasswdException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Utilizador {
    private String name;
    private MediaMap userMedia;
    private String email;
    private String passwd;
    private boolean isLogged;
    private List<Utilizador> friends;
    private List<Playlist> savedPlaylists;

    Utilizador(String email, String name) {
        this.name = name;
        this.email = email;

        this.isLogged = false;

        //Null passwd to force user to set it on first login
        //Better alternatives are welcome
        this.passwd = null;
        this.userMedia = new MediaMap(this);
        this.friends = new ArrayList<>();
        this.savedPlaylists = new ArrayList<>();
    }

    Utilizador(String email, String name, String passwd) {
        this.email = email;
        this.name = name;
        this.isLogged = false;
        this.passwd = passwd;
        this.userMedia = new MediaMap(this);
        this.friends = new ArrayList<>();
        this.savedPlaylists = new ArrayList<>();
    }

    String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    void checkPasswd(String passwd)
            throws InvalidPasswordException, NonSettedPasswdException {
        if(this.passwd == null)
            throw new NonSettedPasswdException();
        if(!this.passwd.equals(passwd))
            throw new InvalidPasswordException();
    }

    void firstPasswdCheck(String new_passwd)
            throws SettedPasswdException {
        if (this.passwd == null) {
            this.passwd = new_passwd;
        }
        throw new SettedPasswdException();
    }

    void login() throws AlreadyLoggedInException {
        if(this.isLogged)
            throw new AlreadyLoggedInException();
        this.isLogged = true;
    }

    void logoff() {
        this.isLogged = false;
    }

    void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPasswd() {
        return passwd;
    }

    void setName(String name) {
        this.name = name;
    }

    //I want some nice upload over the air
    Media uploadMedia(String mediaPath) throws IOException {
        Path old = Paths.get(mediaPath);
        Path newer = Paths.get(".media");
        if(Files.notExists(newer)) {
            Files.createDirectory(newer);
        }
        Path file = newer.resolve(old.getFileName());
        if(Files.notExists(file))
            Files.copy(old, file);
        Media newMedia = new Musica(this, file);
        this.userMedia.put(newMedia.getName(), newMedia);
        return newMedia;
    }

    void removeMedia(String media_id) {
        this.userMedia
                .remove(media_id)
                .getPath()
                .toFile()
                .delete();
    }

    MediaMap getUserMedia() {
        return userMedia;
    }
}