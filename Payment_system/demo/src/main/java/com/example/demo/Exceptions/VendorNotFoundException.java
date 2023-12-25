package com.example.demo.Exceptions;

public class VendorNotFoundException extends RuntimeException {
    public VendorNotFoundException(String msg)
    {
        super(msg);
    }
}
