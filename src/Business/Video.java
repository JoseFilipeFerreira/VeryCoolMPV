package Business;

import java.nio.file.Path;

public class Video extends Media {
    private String serie;
    private int season;
    private int episode;

    Video(Utilizador owner, Path path) {
        super(owner, path);
    }

    public Video(String owner, String path, String nome, String serie, int season, int episode) {
        super(owner, path, nome);
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