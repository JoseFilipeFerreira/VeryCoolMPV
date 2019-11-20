import Exceptions.InvalidGenreException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Musica implements Media {
    List<Utilizador> owner;
    String nome;
    Path path;
    Categoria cat;

    Musica (Utilizador owner, Path path, String cat)
            throws InvalidGenreException {
        this.path = path;
        this.owner = new ArrayList<>();
        this.owner.add(owner);
        this.nome = path.getFileName().toString();
        this.cat = new Categoria(cat);
    }

    //Maybe do this with a setter
    void updateCat(String new_cat)
            throws InvalidGenreException {
        this.cat = new Categoria(new_cat);
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