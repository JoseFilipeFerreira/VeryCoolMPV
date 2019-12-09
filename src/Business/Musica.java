package Business;

import Exceptions.InvalidGenreException;

import java.nio.file.Path;

public class Musica extends Media {
    Categoria cat;
    String album;
    String singer;
    int faixa;

    Musica (Utilizador owner, Path path, String cat)
            throws InvalidGenreException {
        super(owner, path);
        this.cat = new Categoria(cat);
    }

    Musica (Utilizador owner, Path path) {
        super(owner, path);
        this.cat = new Categoria();
    }

    Musica(String name, String path, String owner, String album,
           String singer, int faixa) {
        super(owner, path, name);
        this.cat = new Categoria();
        this.album = album;
        this.singer = singer;
        this.faixa = faixa;
    }

    String getAlbum() {
        return album;
    }

    String getSinger() {
        return singer;
    }

    int getFaixa() {
        return faixa;
    }

    //Maybe do this with a setter
    void updateCat(String new_cat)
            throws InvalidGenreException {
        this.cat = new Categoria(new_cat);
    }

    public int getCat() {
         return this.cat.getPos();
    }
}