package Business;

import java.util.ArrayList;
import java.util.List;

public class MediaPlayer implements Runnable {
    private List<Media> list;

    MediaPlayer(List<Media> to_play) {
        this.list = new ArrayList<>();
        this.list.addAll(to_play);

        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        this.list.forEach(Media::play);
    }
}
