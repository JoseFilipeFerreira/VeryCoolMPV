package business;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Biblioteca {
    private Map<String, Media> library;

    Biblioteca() {
        this.library = new HashMap<>();
    }

    Map<String, Media> getLibrary() {
        return library;
    }

    void addMedia(Media a) {
        this.library.put(a.getName(), a);
    }

    Optional<Media> rmMedia(String media_id) {
        return Optional.ofNullable(this.library.remove(media_id));
    }

    void playAll() {
        this.library
                .values()
                .forEach(Media::play);
    }

    //TODO Random playlists
    Playlist createPlaylist(Predicate<? super Media> filter) {
        return new Playlist(this.library
                .values()
                .stream()
                .filter(filter)
                .collect(Collectors.toList()));
    }
}