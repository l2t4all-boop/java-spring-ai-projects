package com.l2t.sia.exception;

public class StateInfoNotFoundException extends RuntimeException {

    public StateInfoNotFoundException(Integer id) {
        super("StateInfo not found with id: " + id);
    }
}
