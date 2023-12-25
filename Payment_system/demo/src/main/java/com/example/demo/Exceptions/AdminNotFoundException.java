package com.example.demo.Exceptions;

public class AdminNotFoundException extends RuntimeException{
    public AdminNotFoundException(String msg)
    {
        super(msg);
    }
}
