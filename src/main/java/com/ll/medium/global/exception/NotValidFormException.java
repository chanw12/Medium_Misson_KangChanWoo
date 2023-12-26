package com.ll.medium.global.exception;

public class NotValidFormException extends RuntimeException{

    public NotValidFormException(){
        super();
    }
    public NotValidFormException(String message){
        super(message);
    }

}
