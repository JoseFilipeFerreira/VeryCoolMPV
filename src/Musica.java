import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Musica implements Media {
    List<Utilizador> owner;
    String nome;
    Path path;
    Categoria cat;

    Musica (Utilizador owner, Path path) {
        this.path = path;
        this.owner = new ArrayList<>();
        this.owner.add(owner);
    }

    public void play() {
        ProcessBuilder a = new ProcessBuilder("mpv", this.path.toString());
        //Next level debug printing
        a.redirectOutput(ProcessBuilder.Redirect.to(new File("hello")));
        try {
            Process p = a.start();
            p.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void download() {
        throw new UnsupportedOperationException();
    }
}