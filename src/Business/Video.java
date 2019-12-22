package Business;

import java.nio.file.Path;
import java.sql.Date;

public class Video extends Media {
    private String serie;
    private Integer season;
    private Integer episode;

    Video(Utilizador owner, Path path) {
        super(owner, path);
    }

    public Video(String owner, String path, String nome, String serie,
                 Integer season, Integer episode, Date release_date) {
        super(owner, path, nome, release_date);
        this.serie = serie;
        this.season = season;
        this.episode = episode;
    }

    public String getSerie() {
        return serie;
    }

    public Integer getSeason() {
        return season;
    }

    public Integer getEpisode() {
        return episode;
    }

    public String toString() {
        StringBuilder s = new StringBuilder(this.getName());
        if(serie != null)
            s.append(" - ").append(serie);
        if (episode != null && season != null)
            s.append(" (").append(episode).append("/").append(season).append(")");
        s.append("(").append(this.getRelease_date().toLocalDate().getYear()).append(")");
        s.append("(Video)");

        return s.toString();
    }
}