package com.example.demo.ServiceImpl;

import com.example.demo.Exceptions.UserNotFoundExeption;
import com.example.demo.Repository.UserRepository;
import com.example.demo.ServiceInterfaces.AdminServices;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminServices {
    @Autowired
    UserRepository userRepository;
    @Override
    public boolean approveUser(int userId, String seceretKey) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundExeption("User not found"));
        return user.getSecret_code().equals(seceretKey);
    }
}
