package com.servlet.Exceptions;

public class MultipleObjectsReturned extends Exception{
    public MultipleObjectsReturned(String errorMessage) {
        super(errorMessage);
    }
}
