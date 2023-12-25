package com.example.demo.Exceptions;

public class UserAlreadyEnrolledException extends RuntimeException{
    public UserAlreadyEnrolledException(String msg)
    {
        super(msg);
    }
}
