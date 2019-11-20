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
        this.nome = path.getFileName().toString();
    }

    public void play() {
        ProcessBuilder a = new ProcessBuilder("mpv", this.path.toString());
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

    public String getName() {
        return this.nome;
    }
}