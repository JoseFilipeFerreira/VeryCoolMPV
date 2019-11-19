import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

public class Biblioteca {
    Map<String, Media> library;

    Biblioteca() {
        this.library = new HashMap<>();
    }

    Map<String, Media> getLibrary() {
        return library;
    }

    void addMedia(Media a) {
        this.library.put(a.getName(), a);
    }

}