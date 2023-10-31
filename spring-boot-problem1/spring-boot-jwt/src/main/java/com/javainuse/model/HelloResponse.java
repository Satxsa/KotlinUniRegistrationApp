package com.javainuse.model;

import java.io.Serializable;

public class HelloResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String message;

    public HelloResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
