package GraphicalUserInterface;

import Exceptions.PermissionDeniedException;
import Exceptions.UserExistsException;
import business.Administrador;
import business.Media;
import business.MediaCenter;
import business.Utilizador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class App {
    private JButton PLAYButton;
    private JPanel PlayPLS;
    private JTextField musicPLS;

    public App(MediaCenter mdia) {
        PLAYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = musicPLS.getText();
                Optional<Media> media = mdia.searchByName(name + ".mp3");
                media.orElseThrow().play();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VeryCoolGUI.jpeg");

        MediaCenter mdia = new MediaCenter();

        Administrador admin = new Administrador("abc", "def");
        Utilizador user = null;
        try {
            user = mdia.createUser(admin, "adeus", "ola");
        } catch (PermissionDeniedException | UserExistsException ignored) {}

        mdia.uploadMedia(user, "/home/mightymime/Music/Xexe_Band-Afoga_o_Ganso.mp3");

        frame.setContentPane(new App(mdia).PlayPLS);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
