package business;

import java.nio.file.Path;

public interface Media {
    void play();

    void download();

    String getName();

    int getCat();

    Path getPath();
}