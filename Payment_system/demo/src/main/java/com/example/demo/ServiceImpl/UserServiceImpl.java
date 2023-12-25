package com.example.demo.ServiceImpl;

import com.example.demo.Exceptions.UserNotFoundExeption;
import com.example.demo.Repository.UserRepository;
import com.example.demo.ServiceInterfaces.AdminServices;
import com.example.demo.ServiceInterfaces.UserServices;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.configuration.JwtServices;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserServices {
    @Autowired
   private UserRepository userRepository;
    String jwtSecret;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public String registerUser(int userid) {
        User user=new User();
        user.setUserid(userid);
        String secret_code= UUID.randomUUID().toString();
        user.setSecret_code(secret_code);
        userRepository.save(user);
        JwtServices jwtSevice=new JwtServices();
        var jwttoken = jwtSevice.generateToken(user);
       return secret_code;
    }
    @Autowired
    AdminServices adminServices;
    LocalDateTime accountActivateTime;
    @Override
    public String ApprovalForAccount(int userId, String secertKey) {
        if(adminServices.approveUser(userId,secertKey))
        {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundExeption("User not found"));
            user.setUserApprovalStatus(true);
            userRepository.save(user);
            accountActivateTime=LocalDateTime.now();
            isCooldownPeriodElapsed(userId,LocalDateTime.now(),accountActivateTime);
            return "You are approved wait for 15 mins to start anything";
        }
        return "You are not approved";
    }

    @Override
    public String enrollForOfflinePayment(int userId,double latitude,double longitude) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundExeption("User not found"));
        if(!user.getUserEnrollmentStatus() && user.getUserApprovalStatus())
        {
            user.setUserEnrollmentStatus(true);
            user.setUserLatitude(latitude);
            user.setUserLongitude(longitude);
            userRepository.save(user);
            return "You are enrolled now for transferring balance from your online account to offline go to http://localhost:8080/bankingapi/wallet/add-offline-balance/{userid}?amount={amount}";
        }
        return "Unable to enroll either you are not approved or you are already enrolled";
    }
    LocalDateTime offlinePaymentsDisabledTime;
    @Override
    public String unenrollForOfflinePayment(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundExeption("User not found"));
        if(user.getUserEnrollmentStatus())
        {
            user.setUserEnrollmentStatus(false);
            offlinePaymentsDisabledTime= LocalDateTime.now();
            userRepository.save(user);
            isCooldownPeriodElapsed(userId,LocalDateTime.now(),offlinePaymentsDisabledTime);
            return "You are now un enrolled wait for 15 mins to do any transactions";
        }
        return "You are already unenrolled";
    }
    public boolean isCooldownPeriodElapsed(int userId, LocalDateTime currentDateTime,LocalDateTime startedDateTime) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getUserEnrollmentStatus()) {
            LocalDateTime disabledTime = offlinePaymentsDisabledTime;
            if (disabledTime != null) {
                LocalDateTime cooldownEndTime = disabledTime.plusMinutes(15);
                return currentDateTime.isAfter(cooldownEndTime);
            }
        }
        return true;
    }
}
