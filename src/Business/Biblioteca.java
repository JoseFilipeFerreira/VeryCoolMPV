package Business;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Biblioteca {
    private Map<String, Media> library;
    private Utilizador owner;

    Biblioteca(Utilizador u) {
        this.owner = u;
        this.library = new HashMap<>();
    }

    Map<String, Media> getLibrary() {
        return library;
    }

    void addMedia(Media a) {
        this.library.put(a.getName(), a);
    }

    //Ported to MediaMap
    Optional<Media> rmMedia(String media_id) {
        return Optional.ofNullable(this.library.remove(media_id));
    }

    //TODO Random playlists
//    PlaylistMap createPlaylist(Predicate<? super Media> filter, Utilizador u) {
//        return new Playlist(this.library
//                .values()
//                .stream()
//                .filter(filter)
//                .collect(Collectors.toList()), u);
//    }
}