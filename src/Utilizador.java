import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Utilizador {
    private String name;
    private Biblioteca userMedia;
    private String email;
    private String passwd;
    private List<Utilizador> friends;

    Utilizador(String email, String name) {
        this.name = name;
        this.email = email;

        //Null passwd to force user to set it on first login
        //Better alternatives are welcome
        this.passwd = null;
        this.userMedia = new Biblioteca();
        this.friends = new ArrayList<>();
    }

    void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    //I want some nice upload over the air
    Media uploadMedia(String mediaPath) throws IOException {
        Path old = Paths.get(mediaPath);
        Path newer = Paths.get(".media");
        if(Files.notExists(newer)) {
            Files.createDirectory(newer);
        }
        Path file = Files.copy(old, newer.resolve(old.getFileName()));
        Media newMedia = new Musica(this, file);
        return newMedia;
    }

    public static void main(String[] args) {
        Utilizador a = new Utilizador("aa", "Manuel");
        try {
            a.uploadMedia("/home/hitler/output-18_11_2019_11_09.mp4").play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}