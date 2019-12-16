package Business;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String title;
    private int id;
    private String owner;
    private List<String> media;
    private boolean shared;

    Playlist(String owner, int id, boolean shared, String title) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.media = new ArrayList<>();
        this.shared = shared;
    }

    void add(String a) {
        this.media.add(a);
    }

    int getId() {
        return id;
    }

    String getOwner() {
        return owner;
    }

    List<String> getMedia() {
        return media;
    }

    boolean is_shared() {
        return this.shared;
    }

    String getTitle() {
        return title;
    }

    boolean isOwner(Utilizador u) {
        return this.owner.equals(u.getEmail());
    }
}
