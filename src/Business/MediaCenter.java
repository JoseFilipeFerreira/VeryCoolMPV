package Business;

import Exceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MediaCenter {
    private MediaDAO mainLibrary;
    private UserDAO registedUsers;
    private Utilizador user;
    private MediaPlayer player;

    public MediaCenter() {
        this.mainLibrary = new MediaDAO();
        this.registedUsers = new UserDAO();
        this.user = null;
        this.player = new MediaPlayer();
    }

    public Utilizador createUser(String email, String name)
            throws PermissionDeniedException, UserExistsException {
        if (user.isAdmin()) {
            if (this.registedUsers.containsKey(email))
                throw new UserExistsException();
            Utilizador novo = new Utilizador(email, name);
            this.registedUsers.put(email, novo);
            return novo;
        }
        throw new PermissionDeniedException();
    }

    public void fstPasswd(String new_passwd)
            throws SettedPasswdException {
        user.firstPasswdCheck(new_passwd);
        this.registedUsers.put(user.getEmail(), user);
    }

    public void passwd(String old_passwd, String new_passwd)
            throws NonSettedPasswdException, InvalidPasswordException {
        user.checkPasswd(old_passwd);
        user.setPasswd(new_passwd);
        this.registedUsers.put(user.getEmail(), user);
    }

    public void chName(String new_name) {
        user.setName(new_name);
        this.registedUsers.put(user.getEmail(), user);
    }

    public void rmUser(String to_rm)
            throws PermissionDeniedException {
        if (!user.isAdmin())
            throw new PermissionDeniedException();
        this.registedUsers.remove(to_rm);
    }

    public void login(String user, String passwd)
            throws NonExistentUserException, InvalidPasswordException,
            AlreadyLoggedInException, NonSettedPasswdException {
        if (!this.registedUsers.containsKey(user)) {
            throw new NonExistentUserException();
        }
        Utilizador log = this.registedUsers.get(user);
        try {
            log.checkPasswd(passwd);
            log.login();
        } catch (NonSettedPasswdException e) {
            this.user = log;
            throw e;
        }
        this.user = log;
    }

    public void guestLogin() {
        this.user = new Convidado();
    }

    public void logout() {
        this.user = null;
    }

    public boolean isAdmin() {
        return this.user.isAdmin();
    }

    public String getEmail() {
        return this.user.getEmail();
    }

    public void uploadMedia(Media path) {
        try {
            user.uploadMedia(path);
        } catch (IOException ignored) {
        }
    }

    List<Media> listAllMedia() {
        return new ArrayList<>(this.mainLibrary
                .values());
    }

    public List<Media> searchByName(String name) {
        return this.mainLibrary.searchByName(name, this.user.getEmail());
    }

    public void rmMedia(Media media) {
        user.removeMedia(media.getName());
    }

    public void chCat(Musica m, String cat) throws
            InvalidGenreException {
        Categoria n = new Categoria(cat);
        m.setCat(n);
        if (m.getOwner().equals(user.getEmail()))
            mainLibrary.updateCat(m.getCat(), m.getName());
        else
            mainLibrary.updateCat(m, user.getEmail());
    }

    public Collection<Media> allMedia() {
        return this.mainLibrary.values(user.getEmail());
    }

    public void playMedia(Media m) {
        this.player.play(m);
    }

    public void playMedia(List<Media> m) {
        this.player.play(m);
    }

    public List<Media> searchByArtist(String artist) {
        return this.mainLibrary.searchByArtist(artist, this.user.getEmail());
    }

    public List<Media> artistMedia(String artist) {
        return this.mainLibrary.artistMedia(artist, this.user.getEmail());
    }

    public List<Media> searchByCat(String cat) {
        return this.mainLibrary.searchByCat(cat, this.user.getEmail());
    }

    public List<Media> searchByArtistUser(String artist) {
        return this.user
                .getUserMedia()
                .searchByArtist(artist, this.user.getEmail());
    }

    public List<Media> artistMediaUser(String artist) {
        return this.user
                .getUserMedia()
                .artistMedia(artist, this.user.getEmail());
    }

    public List<Media> searchByCatUser(String cat) {
        return this.user
                .getUserMedia()
                .searchByCat(cat, this.user.getEmail());
    }

    public Collection<Media> allMediaUser() {
        return this.user
                .getUserMedia()
                .values(user.getEmail());
    }

    public List<Media> searchByNameUser(String name) {
        return this.user
                .getUserMedia()
                .searchByName(name, this.user.getEmail());
    }

    public void togglePause() {
        if (this.player != null)
            this.player.togglePause();
    }

    public void next() {
        if (this.player != null)
            this.player.next();
    }

    public void prev() {
        if (this.player != null)
            this.player.previous();
    }

    public void stop() {
        if (this.player != null)
            this.player.stop();
    }
}