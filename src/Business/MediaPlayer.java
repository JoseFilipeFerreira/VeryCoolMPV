package Business;

import java.io.IOException;
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
        List<String> paths = new ArrayList<>();
        paths.add("mpv");
        paths.add("--no-audio-display");
        this.list.forEach(x -> paths.add(x.getPath().toString()));
        paths.forEach(System.out::println);
        ProcessBuilder a = new ProcessBuilder(paths);
        try {
            Process p = a.start();
            p.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
