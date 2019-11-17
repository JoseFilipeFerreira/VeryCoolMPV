import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class Musica implements Media {
    Utilizador owner;
    byte[] content;

    Musica (Utilizador owner, byte[] content) {
        this.content = content;
        this.owner = owner;
    }

    public void play() {
        ProcessBuilder a = new ProcessBuilder("mpv", "-");
        a.redirectOutput(ProcessBuilder.Redirect.to(new File("hello")));
        try {
            Process p = a.start();
            OutputStream o = p.getOutputStream();
            o.write(this.content);
            o.flush();
            p.waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void download() {
    }
}