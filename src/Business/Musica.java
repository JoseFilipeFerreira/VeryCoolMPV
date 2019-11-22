package Business;

import Exceptions.InvalidGenreException;

import java.nio.file.Path;

public class Musica extends Media {
    Categoria cat;

    Musica (Utilizador owner, Path path, String cat)
            throws InvalidGenreException {
        super(owner, path);
        this.cat = new Categoria(cat);
    }

    Musica (Utilizador owner, Path path) {
        super(owner, path);
        this.cat = new Categoria();
    }

    //Maybe do this with a setter
    void updateCat(String new_cat)
            throws InvalidGenreException {
        this.cat = new Categoria(new_cat);
    }

    public void download() {
        throw new UnsupportedOperationException();
    }

    public int getCat() {
         return this.cat.getPos();
    }
}