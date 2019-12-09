package Business;

import java.nio.file.Path;

public class Video extends Media {
    private String titulo;
    private String serie;
    private int season;
    private int episode;

    Video(Utilizador owner, Path path) {
        super(owner, path);
    }

    public int getCat() {
         throw new UnsupportedOperationException();
    }
}