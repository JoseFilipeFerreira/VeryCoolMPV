package Business;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class Media {
    private List<Utilizador> owner;
    private String nome;
    private Path path;

    Media(Utilizador owner, Path path) {
        this.path = path;
        this.owner = new ArrayList<>();
        this.owner.add(owner);
        this.nome = path.getFileName().toString();
    }

    void download(){}

    String getName() {
        return nome;
    }

    abstract int getCat();

    public void play() {
        ProcessBuilder a = new ProcessBuilder("mpv", this.getPath().toString());
        try {
            Process p = a.start();
            p.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    Path getPath() {
        return path;
    }
}