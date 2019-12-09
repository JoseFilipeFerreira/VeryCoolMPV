package Exceptions;

import Business.Utilizador;

public class NonSettedPasswdException extends Exception {
    private Utilizador user;

    public NonSettedPasswdException(Utilizador user) {
        this.user = user;
    }

    public Utilizador getUser() {
        return this.user;
    }
}
