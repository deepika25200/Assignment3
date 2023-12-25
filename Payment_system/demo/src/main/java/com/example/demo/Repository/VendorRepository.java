package com.example.demo.Repository;

import com.example.demo.model.Vendor;
import com.example.demo.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor,Long> {

    Optional<Object> findByVendorId(long vendorId);
}
