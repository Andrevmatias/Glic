package br.tcc.glic.exceptions;

/**
 * Created by André on 01/05/2016.
 */
public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String message){
        super(message);
    }
}
