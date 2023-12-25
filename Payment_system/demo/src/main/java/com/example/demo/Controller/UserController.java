package com.example.demo.Controller;

import com.example.demo.ServiceInterfaces.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("/bankingapi/users") // Correct the base path
public class UserController {
    @Autowired
    UserServices userServices;
    @PostMapping("/register/{id}") // Keep the "/" as it represents the endpoint relative to the base path
    public String register(@PathVariable int id)
    {
        return "your secret code is "+userServices.registerUser(id);
    }
    @PostMapping("/enrollforonline/{latitude}/{longitude}")
    public String enroll(@PathVariable double latitude,@PathVariable double longitude,@RequestParam("userId") int userId){
        return userServices.enrollForOfflinePayment(userId,latitude,longitude);}
    @PostMapping("/unenrollforonline/{id}")
    public String unenroll(@PathVariable int id){return  userServices.unenrollForOfflinePayment(id);}
    @GetMapping("/approval/{id}")
    public String approval(@PathVariable("userId") int userId, @RequestParam("secretkey") String secretkey)
    {
        return userServices.ApprovalForAccount(userId,secretkey);
    }
}
