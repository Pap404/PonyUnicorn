package com.petshop.enterpraise.config;

public class PonyStorageEx extends RuntimeException {

    public PonyStorageEx(String message) {
        super(message);
    }

    public PonyStorageEx(String message, Throwable cause) {
        super(message, cause);
    }
}