public class Administrador extends Utilizador{

    private Administrador(String email) {
        super(email, "heelo");
    }

    Utilizador createUser(String userName, String name) {
        return new Utilizador(userName, name);
    }
}