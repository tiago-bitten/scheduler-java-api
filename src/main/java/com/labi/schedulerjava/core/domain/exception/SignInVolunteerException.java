package com.labi.schedulerjava.core.domain.exception;

public class SignInVolunteerException extends RuntimeException {

    public SignInVolunteerException() {
        super("Confira sua data de nascimento e tente novamente");
    }
}
