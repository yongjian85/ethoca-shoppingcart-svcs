package com.yxj.ethoca.Exceptions;

public class DataSaveException extends Exception {

    public DataSaveException () {
        super("Unable to save data to repository");
    }

}
