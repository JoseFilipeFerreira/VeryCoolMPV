package Business;

public class Convidado extends Utilizador {
    private static long guestId = 0;
    private boolean loggedId;

    Convidado() {
        super("guest".concat(String.valueOf(guestId++)), "Guest");
    }
}