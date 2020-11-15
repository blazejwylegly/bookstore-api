package com.io.bookstoreapi.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("User with given email already exists!");
    }

    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
