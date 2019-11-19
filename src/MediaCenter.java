import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MediaCenter {
    //Saving each user library is easier to remove media
    private Map<String, Biblioteca> mainLibrary;
    private Map<String, Utilizador> registedUsers;

    MediaCenter() {
        this.mainLibrary = new HashMap<>();
        this.registedUsers = new HashMap<>();
    }

    //This needs to be better managed
    Utilizador createUser(Utilizador user, String email, String name) throws PermissionDeniedException {
        if(user instanceof Administrador) {
            Utilizador novo = new Utilizador(email, name);
            this.registedUsers.put(email, novo);
            this.mainLibrary.put(email, novo.getUserMedia());
            return novo;
        }
        throw new PermissionDeniedException();
    }

    //Maybe throw some exceptions to know whats going on
    //Or allow multiple logins, but I don't think that's a good idea
    Optional<Utilizador> login(String user, String passwd) {
        if(!this.registedUsers.containsKey(user)) {
            return Optional.empty();
        }
        Utilizador log = this.registedUsers.get(user);
        if(!log.checkPasswd(passwd) || log.alreadyLoggedIn())
            return Optional.empty();
        log.login();
        return Optional.of(log);
    }

    void uploadMedia(Utilizador user, String path) {
        try {
            user.uploadMedia(path);
        } catch (IOException ignored) {}
    }

    //We can implement partial searching in the future
    //I even think we can use the youtube api to play search videos
    Optional<Media> searchByName(String name) {
        return this.mainLibrary.values()
                .stream()
                .flatMap(x -> x.getLibrary().values().stream())
                .filter(x -> x.getName().equals(name))
                .findFirst();
    }

    Biblioteca userLibrary(Utilizador user) {
        return this.mainLibrary.get(user.getEmail());
    }

    public static void main(String[] args) {
        MediaCenter mdia = new MediaCenter();
        Administrador admin = new Administrador("abc", "def");
        try {
            Utilizador user = mdia.createUser(admin, "adeus", "ola");
            mdia.uploadMedia(user, "/home/hitler/Downloads/ola.mp3");
            Optional<Media> media = mdia.searchByName("ola.mp3");
            media.orElseThrow().play();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
    }
}