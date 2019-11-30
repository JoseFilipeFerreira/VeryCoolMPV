package Business;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Media {
    String owner;
    private String nome;
    private String path;

    Media(Utilizador owner, Path path) {
        this.path = path.toString();
        this.owner = owner.getEmail();
        this.nome = path.getFileName().toString();
    }

    Media(String owner, String path, String nome) {
        this.path = path;
        this.owner = owner;
        this.nome = nome;
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
        return Paths.get(this.path);
    }

    public String getOwner() {
        return this.owner;
    }
}