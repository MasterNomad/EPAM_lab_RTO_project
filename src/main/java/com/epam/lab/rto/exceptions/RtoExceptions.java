package com.epam.lab.rto.exceptions;

public abstract class RtoExceptions extends RuntimeException {

    public RtoExceptions() {
    }

    public RtoExceptions(String message) {
        super(message);
    }
}
