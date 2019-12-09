package Business;

import Exceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediaCenter {
    //Saving each user library is easier to remove media
    private MediaMap mainLibrary;
    private UserMap registedUsers;

    public MediaCenter() {
        this.mainLibrary = new MediaMap();
        this.registedUsers = new UserMap();
    }

    //This needs to be better managed
    public Utilizador createUser(Utilizador user, String email, String name)
            throws PermissionDeniedException, UserExistsException {
        if(user instanceof Administrador) {
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

    public void passwd(Utilizador u, String old_passwd, String new_passwd)
            throws NonSettedPasswdException, InvalidPasswordException {
        u.checkPasswd(old_passwd);
        u.setPasswd(new_passwd);
        this.registedUsers.put(u.getEmail(), u);
    }

    public void passwd(Administrador u, String old_passwd, String new_passwd)
            throws NonSettedPasswdException, InvalidPasswordException {
        u.checkPasswd(old_passwd);
        u.setPasswd(new_passwd);
        this.registedUsers.put(u.getEmail(), u);
    }

    public void chName(Administrador u, String new_name) {
        u.setName(new_name);
        this.registedUsers.put(u.getEmail(), u);
    }

    public void chName(Utilizador u, String new_name) {
        u.setName(new_name);
        this.registedUsers.put(u.getEmail(), u);
    }

    void rmUser(Utilizador admin, Utilizador to_rm)
            throws PermissionDeniedException {
        if(! (admin instanceof Administrador))
            throw new PermissionDeniedException();
        this.mainLibrary.remove(to_rm.getEmail());
        this.registedUsers.remove(to_rm.getEmail());
    }

    void rmUser(Utilizador admin, String to_rm)
            throws PermissionDeniedException {
        if(! (admin instanceof Administrador))
            throw new PermissionDeniedException();
        this.mainLibrary.remove(to_rm);
        this.registedUsers.remove(to_rm);
    }

    public Utilizador login(String user, String passwd)
            throws NonExistentUserException, InvalidPasswordException,
            AlreadyLoggedInException, NonSettedPasswdException
    {
        if(!this.registedUsers.containsKey(user)) {
            throw new NonExistentUserException();
        }
        Utilizador log = this.registedUsers.get(user);
        log.checkPasswd(passwd);
        log.login();
        return log;
    }

    public void uploadMedia(Utilizador user, String path) {
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

    void rmMedia(Utilizador user, String media_id) {
        user.removeMedia(media_id);
    }
}