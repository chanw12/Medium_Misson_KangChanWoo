package com.ll.medium.global.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(){
        super();
    }
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }


}
