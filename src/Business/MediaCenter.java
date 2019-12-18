package Business;

import Exceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediaCenter {
    //Saving each user library is easier to remove media
    private MediaMap mainLibrary;
    private UserMap registedUsers;
    private Utilizador user;

    public MediaCenter() {
        this.mainLibrary = new MediaMap();
        this.registedUsers = new UserMap();
    }

    //This needs to be better managed
    public Utilizador createUser(String email, String name)
            throws PermissionDeniedException, UserExistsException {
        if(user.isAdmin()) {
            if(this.registedUsers.containsKey(email))
                throw new UserExistsException();
            Utilizador novo = new Utilizador(email, name);
            this.registedUsers.put(email, novo);
            return novo;
        }
        throw new PermissionDeniedException();
    }

    public void fstPasswd(Utilizador u, String new_passwd)
            throws SettedPasswdException {
        u.firstPasswdCheck(new_passwd);
        this.registedUsers.put(u.getEmail(), u);
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
        if(!user.isAdmin())
            throw new PermissionDeniedException();
        this.registedUsers.remove(to_rm);
    }

    public void login(String user, String passwd)
            throws NonExistentUserException, InvalidPasswordException,
            AlreadyLoggedInException, NonSettedPasswdException
    {
        if(!this.registedUsers.containsKey(user)) {
            throw new NonExistentUserException();
        }
        Utilizador log = this.registedUsers.get(user);
        log.checkPasswd(passwd);
        log.login();
        this.user = log;
    }

    public void uploadMedia(Media path) {
        try {
            user.uploadMedia(path);
        } catch (IOException ignored) {}
    }

    List<Media> listAllMedia() {
       return new ArrayList<>(this.mainLibrary
               .values());
    }

    public List<Media> searchByName(String name) {
        return this.mainLibrary.searchByName(name);
    }

    void rmMedia(String media_id) {
        user.removeMedia(media_id);
    }

    public void chCat(Musica m, String cat) throws
            InvalidGenreException {
        Categoria n = new Categoria(cat);
        m.setCat(n);
        if(m.getOwner().equals(user.getEmail()))
            mainLibrary.updateCat(m.getCat(), m.getName());
        else
            mainLibrary.updateCat(m, user.getEmail());
    }

    public void playMedia(Media m) {
        List<Media> a = new ArrayList<>();
        a.add(m);
        new MediaPlayer(a);
    }

    public void playMedia(List<Media> m) {
        new MediaPlayer(m);
    }
}