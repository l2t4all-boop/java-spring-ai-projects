package com.l2t.sia.exception;

public class StateInfoAlreadyExistsException extends RuntimeException {

    public StateInfoAlreadyExistsException(Integer id) {
        super("StateInfo already exists with id: " + id);
    }
}
