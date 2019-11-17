public class Administrador extends Utilizador{

    private Administrador(String email) {
        super(email);
    }

    Utilizador createUser(String userName) {
        return new Utilizador(userName);
    }
}