package com.example.demo.Exceptions;

public class UserNotFoundExeption extends RuntimeException {
    public UserNotFoundExeption(String msg)
    {
        super(msg);
    }
}
