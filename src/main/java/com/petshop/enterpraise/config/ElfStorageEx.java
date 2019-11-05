package com.petshop.enterpraise.config;

public class ElfStorageEx extends RuntimeException {

    public ElfStorageEx(String message) {
        super(message);
    }

    public ElfStorageEx(String message, Throwable cause) {
        super(message, cause);
    }
}