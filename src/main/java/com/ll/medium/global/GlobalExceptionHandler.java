package com.ll.medium.global;

import com.ll.medium.global.exception.NotValidFormException;
import com.ll.medium.global.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleUserAlreadyExistsException(UserAlreadyExistsException e){
        Map<String,String> errorResponse = new HashMap<>();
        errorResponse.put("error","UserAlreadyExists");
        errorResponse.put("message",e.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(NotValidFormException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<String> handleNotValidFormException(NotValidFormException e){
        String[] errors = e.getMessage().split("////");
        return new ArrayList<>(Arrays.asList(errors));
    }


}
