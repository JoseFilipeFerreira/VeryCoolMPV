package Business;

import Exceptions.InvalidGenreException;
import Exceptions.InvalidMusicException;

import java.io.IOException;
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
        this.singer = singer;
        this.faixa = faixa;
        this.album = album;
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

    void setCat(Categoria cat) {
        this.id_cat = cat.getPos();
    }

    void play() {
        ProcessBuilder a = new ProcessBuilder("mpv", "--no-video",
                this.getPath().toString());
        try {
            Process p = a.start();
            p.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        try {
            return "Music: " + singer + " - " + this.getName() + " (" + album + ") (" + new Categoria(id_cat).toString() + ") (" + this.getRelease_date().toLocalDate().getYear() + ")";
        } catch (InvalidGenreException e) {
            return "Music: apita o comboio";
        }
    }
}