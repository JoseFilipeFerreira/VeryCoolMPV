package Business;

import java.util.List;

public class Playlist extends Biblioteca {
    private boolean shared;

    Playlist(Utilizador owner) {
        super(owner);
        this.shared = false;
    }

    Playlist(List<Media> media, Utilizador u) {
        super(u);
        this.shared = false;
        media.forEach(this::addMedia);
    }

    Playlist(boolean shared, Utilizador u) {
        super(u);
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
