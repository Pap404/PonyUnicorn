package com.petshop.enterpraise.config;

public class UnicornStorageEx extends RuntimeException {

    public UnicornStorageEx(String message) {
        super(message);
    }

    public UnicornStorageEx(String message, Throwable cause) {
        super(message, cause);
    }
}