package com.example.demo.ServiceInterfaces;

import com.example.demo.model.User;

import java.util.List;

public interface UserServices {
    public List<User> getAllUsers();
    public String registerUser(int userId);
    public String ApprovalForAccount(int userId,String secertKey);
    public String enrollForOfflinePayment(int userId,double latitude,double longitude);
    public String unenrollForOfflinePayment(int userId);
}
