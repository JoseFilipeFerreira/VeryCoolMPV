package business;

import java.util.List;

public class Playlist extends Biblioteca {
    private boolean shared;

    Playlist() {
        super();
        this.shared = false;
    }

    Playlist(List<Media> media) {
        super();
        this.shared = false;
        media.forEach(this::addMedia);
    }

    Playlist(boolean shared) {
        super();
        this.shared = shared;
    }

    boolean isShared() {
        return shared;
    }

    boolean toggleShared() {
        this.shared = !this.shared;
        return this.shared;
    }
}
