package Business;

import Exceptions.InvalidGenreException;
import Exceptions.InvalidMusicException;

import java.nio.file.Path;
import java.sql.Date;

public class Musica extends Media {
    int id_cat;
    String album;
    String singer;
    int faixa;

    Musica (Utilizador owner, Path path, int cat)
            throws InvalidGenreException {
        super(owner, path);
        this.id_cat = cat;
    }

    Musica (Utilizador owner, Path path) {
        super(owner, path);
        this.id_cat = -1;
    }

    public Musica(String name, String path, String owner, String album,
           String singer, int faixa, Date release_date, String cat) throws
            InvalidMusicException, InvalidGenreException {
        super(owner, path, name, release_date);
        if(singer == null)
            throw new InvalidMusicException();
        this.id_cat = -1;
        this.album = album;
        this.singer = singer;
        this.faixa = faixa;
        this.id_cat = new Categoria(cat).getPos();
    }

    public Musica(String name, String path, String owner, String album,
                  String singer, int faixa, Date release_date, int cat) throws
            InvalidMusicException, InvalidGenreException {
        super(owner, path, name, release_date);
        if(singer == null)
            throw new InvalidMusicException();
        this.id_cat = -1;
        this.album = album;
        this.singer = singer;
        this.faixa = faixa;
        this.id_cat = new Categoria(cat).getPos();
    }

    public Musica(String name, String path, Utilizador owner, String album,
           String singer, int faixa, Date release_date, String cat) throws
            InvalidMusicException, InvalidGenreException {
        super(owner.getEmail(), path, name, release_date);
        if(singer == null)
            throw new InvalidMusicException();
        this.id_cat = -1;
        this.album = album;
        this.singer = singer;
        this.faixa = faixa;
        this.id_cat = new Categoria(cat).getPos();
    }


    public Musica(String name, String path, Utilizador owner, String album,
                  String singer, int faixa, Date release_date, int cat) throws
            InvalidMusicException, InvalidGenreException {
        super(owner.getEmail(), path, name, release_date);
        if(singer == null)
            throw new InvalidMusicException();
        this.id_cat = -1;
        this.album = album;
        this.singer = singer;
        this.faixa = faixa;
        this.id_cat = new Categoria(cat).getPos();
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

    void updateCat(int new_cat)
            throws InvalidGenreException {
        this.id_cat = new_cat;
    }

    public int getCat() {
         return this.id_cat;
    }
}