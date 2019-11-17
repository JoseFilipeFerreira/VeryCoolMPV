import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utilizador {
    private Biblioteca userMedia;
    private String email;
    private String passwd;

    Utilizador(String email) {
        this.email = email;
        //Null passwd to force user to set it on first login
        this.passwd = null;
        this.userMedia = new Biblioteca();
    }

    void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    void uploadMedia(String mediaPath) {
        try {
            Path path = Paths.get(mediaPath);
            byte[] data = Files.readAllBytes(path);
            Media newMedia = new Musica(this, data);
            newMedia.play();
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    public static void main(String[] args) {
        Utilizador a = new Utilizador("aa");
        a.uploadMedia("/home/hitler/output-17_11_2019_17_00.mp4");
    }
}