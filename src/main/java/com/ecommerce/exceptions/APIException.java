package com.ecommerce.exceptions;

public class APIException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public APIException() {
        System.out.println("Inside the APIExceptin No Args Constructor");
    }

    public APIException(String message){
        super(message);
        System.out.println("Inside the APIException Args Constructor");
    }
}
