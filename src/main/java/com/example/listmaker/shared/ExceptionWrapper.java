package com.example.listmaker.shared;

import java.io.Serializable;

/**
 * Generic error class that wraps a server exception for the REST client
 *
 * Created by david on 7/15/14.
 */
public class ExceptionWrapper implements Serializable {

    private final String className;
    private final String msg;

    public ExceptionWrapper(Exception e) {
        this.className = e.getClass().getName();
        this.msg = e.getMessage();
    }

    public String getClassName() {
        return className;
    }

    public String getMsg() {
        return msg;
    }
}
