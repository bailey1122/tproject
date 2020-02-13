package com.pxsy.exceptions;

// user-defined exception
public class NoFileAvailableException extends RuntimeException {

    private String fileName;

    public NoFileAvailableException(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
