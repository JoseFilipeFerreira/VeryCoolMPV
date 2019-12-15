package Business;

import java.nio.file.Path;
import java.sql.Date;

public class Video extends Media {
    private String serie;
    private int season;
    private int episode;

    Video(Utilizador owner, Path path) {
        super(owner, path);
    }

    public Video(String owner, String path, String nome, String serie,
                 int season, int episode, Date release_date) {
        super(owner, path, nome, release_date);
        this.serie = serie;
        this.season = season;
        this.episode = episode;
    }

    public Video(Utilizador owner, String path, String nome, String serie,
                 int season, int episode, Date release_date) {
        super(owner.getEmail(), path, nome, release_date);
        this.serie = serie;
        this.season = season;
        this.episode = episode;
    }

    public String getSerie() {
        return serie;
    }

    public int getSeason() {
        return season;
    }

    public int getEpisode() {
        return episode;
    }

    public int getCat() {
         throw new UnsupportedOperationException();
    }
}