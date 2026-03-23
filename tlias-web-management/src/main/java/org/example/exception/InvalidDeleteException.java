package org.example.exception;

public class InvalidDeleteException extends RuntimeException{

    public InvalidDeleteException(String s) {
        super(s);
    }
}
