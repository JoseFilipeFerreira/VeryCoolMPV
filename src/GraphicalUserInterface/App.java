package GraphicalUserInterface;

import Business.Administrador;
import Business.MediaCenter;
import Business.Utilizador;
import Exceptions.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    private JPanel Login;
    private JButton LoginButton;
    private JTextField user;
    private JPasswordField password;
    private JLabel userLbl;
    private JLabel passwdLbl;

    public App(MediaCenter mdia) {
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String u = user.getText();
                String p = password.getText();

                try {
                    mdia.login(u, p);
                } catch (NonExistentUserException | InvalidPasswordException | AlreadyLoggedInException e) {
                    password.setText("");
                    JOptionPane.showMessageDialog(null,"Invalid Credentials");
                } catch (NonSettedPasswdException e) {
                    JOptionPane.showMessageDialog(null,"Password not set");
                }
            }
        });
    }

    public static void main(String[] args) {
        MediaCenter mdia = new MediaCenter();

        Administrador admin = new Administrador("abc", "def");
        Utilizador user = null;
        try {
            user = mdia.createUser(admin, "adeus", "ola");
        } catch (PermissionDeniedException | UserExistsException ignored) {}

        mdia.uploadMedia(user, "/home/mightymime/Music/Xexe_Band-Afoga_o_Ganso.mp3");

        JFrame frame = new JFrame("VeryCoolGUI.jpeg");
        frame.setContentPane(new App(mdia).Login);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
