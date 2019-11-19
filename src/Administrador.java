public class Administrador extends Utilizador{

    Administrador(String email, String name) {
        super(email, name);
    }

    Utilizador createUser(String userName, String name) {
        return new Utilizador(userName, name);
    }
}