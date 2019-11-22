package Business;

import java.nio.file.Path;

public class Video extends Media {

    Video(Utilizador owner, Path path) {
        super(owner, path);
    }

    public int getCat() {
         throw new UnsupportedOperationException();
    }
}