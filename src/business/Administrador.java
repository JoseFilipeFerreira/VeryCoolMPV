package business;

public class Administrador extends Utilizador {

    public Administrador(String email, String name) {
        super(email, name);
    }

    Utilizador createUser(String userName, String name) {
        return new Utilizador(userName, name);
    }
}