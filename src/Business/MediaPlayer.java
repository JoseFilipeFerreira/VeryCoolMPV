package Business;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MediaPlayer implements Runnable {
    private List<Media> list;
    private boolean playing;

    MediaPlayer() {
        this.list = new ArrayList<>();
        this.playing = false;
    }

    void play (Media to_play) {
        this.list.add(to_play);
        if(!this.playing) {
            Thread t = new Thread(this);
            t.start();
        }
        else
            this.queue(to_play);
    }

    void play(List<Media> to_play) {
        this.list.addAll(to_play);
        if(!this.playing) {
            this.playing = true;
            Thread t = new Thread(this);
            t.start();
        }
        else
            this.queue(to_play);
    }

    void next() {
        ProcessBuilder a = new ProcessBuilder("socat", "-", "/tmp/mpvsocket");
        try {
            Process p = a.start();
            new PrintWriter(new BufferedOutputStream(p.getOutputStream()),
                    true).println("playlist-next");
            p.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    void previous() {
        ProcessBuilder a = new ProcessBuilder("socat", "-", "/tmp/mpvsocket");
        try {
            Process p = a.start();
            new PrintWriter(new BufferedOutputStream(p.getOutputStream()),
                    true).println("playlist-prev");
            p.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void queue(List<Media> to_play) {
        for(Media m: to_play) {
            ProcessBuilder a = new ProcessBuilder("./play.sh", m.getPath().toString());
            try {
                Process p = a.start();
                p.waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void queue(Media to_play) {
        ProcessBuilder a = new ProcessBuilder("./play.sh", to_play.getPath().toString());
        try {
            Process p = a.start();
            p.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    void togglePause() {
        ProcessBuilder a = new ProcessBuilder("socat", "-", "/tmp/mpvsocket");
        try {
            Process p = a.start();
            new PrintWriter(new BufferedOutputStream(p.getOutputStream()),
                    true).println("cycle pause");
            p.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        List<String> paths = new ArrayList<>();
        paths.add("mpv");
        paths.add("--no-audio-display");
        paths.add("--input-ipc-server=/tmp/mpvsocket");
        this.list.forEach(x -> paths.add(x.getPath().toString()));
        ProcessBuilder a = new ProcessBuilder(paths);
        try {
            Process p = a.start();
            p.waitFor();
            this.playing = false;
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
