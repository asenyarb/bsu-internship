package com.servlet.Exceptions;

public class DoesNotExist extends Exception {
    public DoesNotExist(String errorMessage) {
        super(errorMessage);
    }
}
