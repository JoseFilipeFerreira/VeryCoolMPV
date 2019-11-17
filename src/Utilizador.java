public class Utilizador {
    private Biblioteca userMedia;
    private String email;
    private String passwd;

    Utilizador(String email) {
        this.email = email;
        //Null passwd to force user to set it on first login
        this.passwd = null;
        this.userMedia = new Biblioteca();
    }

    void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    void uploadMedia() {}
}