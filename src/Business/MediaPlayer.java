package Business;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MediaPlayer implements Runnable {
    private List<Media> list;
    private boolean playing;
    private Process mpv;

    MediaPlayer() {
        this.list = new ArrayList<>();
        this.playing = false;
        this.mpv = null;
    }

    void play(Media to_play) {
        this.list.add(to_play);
        if(!this.playing) {
            this.playing = true;
            Thread t = new Thread(this);
            t.start();
        } else
            this.queue(to_play);
    }

    void play(List<Media> to_play) {
        this.list.addAll(to_play);
        if (!this.playing) {
            this.playing = true;
            Thread t = new Thread(this);
            t.start();
        } else
            this.queue(to_play);
    }

    void next() {
        ProcessBuilder a = new ProcessBuilder("socat", "-", "/tmp/mpvsocket");
        try {
            Process p = a.start();
            new PrintWriter(new BufferedOutputStream(p.getOutputStream()),
                    true).println("playlist-next");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void previous() {
        ProcessBuilder a = new ProcessBuilder("socat", "-", "/tmp/mpvsocket");
        try {
            Process p = a.start();
            new PrintWriter(new BufferedOutputStream(p.getOutputStream()),
                    true).println("playlist-prev");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void queue(List<Media> to_play) {
        for (Media m : to_play) {
            ProcessBuilder a = new ProcessBuilder("./play.sh", m.getPath().toString());
            try {
                Process p = a.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void queue(Media to_play) {
        ProcessBuilder a = new ProcessBuilder("./play.sh", to_play.getPath().toString());
        try {
            Process p = a.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void togglePause() {
        ProcessBuilder a = new ProcessBuilder("socat", "-", "/tmp/mpvsocket");
        try {
            Process p = a.start();
            new PrintWriter(new BufferedOutputStream(p.getOutputStream()),
                    true).println("cycle pause");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void stop() {
        if(this.mpv != null)
            this.mpv.destroy();
    }

    public void run() {
        List<String> paths = new ArrayList<>();
        paths.add("mpv");
        paths.add("--no-audio-display");
        paths.add("--input-ipc-server=/tmp/mpvsocket");
        this.list.forEach(x -> paths.add(x.getPath().toString()));
        ProcessBuilder a = new ProcessBuilder(paths);
        try {
            this.mpv = a.start();
            this.mpv.waitFor();
            this.playing = false;
            this.list.clear();
            this.mpv = null;
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
