package com.labi.schedulerjava.core.domain.exception;

public class UserAccountNotApprovedException extends RuntimeException {

    public UserAccountNotApprovedException() {
        super("Sua conta ainda não foi aprovada.");
    }
}
