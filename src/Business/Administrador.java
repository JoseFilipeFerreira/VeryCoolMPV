package Business;

public class Administrador extends Utilizador {

    public Administrador(String email, String name) {
        super(email, name);
    }

    public Administrador(String email, String name, String passwd) {
        super(email, name, passwd);
    }

    Utilizador createUser(String userName, String name) {
        return new Utilizador(userName, name);
    }
}