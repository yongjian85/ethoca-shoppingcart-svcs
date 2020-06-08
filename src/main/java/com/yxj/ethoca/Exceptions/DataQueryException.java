package com.yxj.ethoca.Exceptions;

public class DataQueryException extends Exception {

    public DataQueryException () {
        super("Unable to retrieve data from datastore");
    }
}
