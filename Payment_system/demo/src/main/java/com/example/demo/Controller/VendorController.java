package com.example.demo.Controller;

import com.example.demo.ServiceInterfaces.VendorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/bankingapi/vendors")
public class VendorController {
    @Autowired
    VendorServices vendorServices;
    @PostMapping("/register/{longitude}/{latitude}")
    public ResponseEntity<String> register(@PathVariable("longitude") Double longitude,
            @PathVariable("latitude") Double latitude,  @RequestParam("vendorId") Long vendorId)
    {
        return vendorServices.registerVendor(vendorId,latitude,longitude);
    }
    @GetMapping("/reuest-approval/{adminId}/{vendorId}")
    public ResponseEntity<String> approval(@PathVariable("adminId") Long adminId, @PathVariable("vendorId") Long vendorId,
                                @RequestParam("walletId") Long walletId)
    {
        return  vendorServices.approveVendor(adminId,vendorId,walletId);
    }
    @GetMapping("/transfer-money/{vendorId}/{walletId}")
    public ResponseEntity<String> transferMoney(@PathVariable("vendorId") Long vendorId , @PathVariable("walletId") Long walletId,
                                                @RequestParam("amount") BigDecimal amount )
    {
        return vendorServices.transferMoney(vendorId,amount,walletId);
    }
}
