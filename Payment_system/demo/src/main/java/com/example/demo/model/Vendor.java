package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name="vendor_details")
public class Vendor{
    @Id
    Long vendorId;
    private String name;
    private Double latitude;
    private Double longitude;
    private Boolean status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_wallet_id")
    private Wallet storeWallet;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_wallet_id")
    private Wallet personalWallet;
    @Enumerated(EnumType.STRING)
    UserType userType;
    public Vendor(Long vendorId,String name, Double latitude, Double longitude, Boolean status, Wallet storeWallet, Wallet personalWallet) {
        this.vendorId=vendorId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.storeWallet = storeWallet;
        this.personalWallet = personalWallet;
        userType=UserType.vendor;
    }

    public Vendor(Long vendorId, double latitude, double longitude) {
        this.vendorId=vendorId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = false;
        this.storeWallet = new Wallet();
        this.personalWallet = new Wallet();
        userType=UserType.vendor;
    }

    public Vendor() {
        userType=UserType.vendor;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Wallet getStoreWallet() {
        return storeWallet;
    }

    public void setStoreWallet(Wallet storeWallet) {
        this.storeWallet = storeWallet;
    }

    public Wallet getPersonalWallet() {
        return personalWallet;
    }

    public void setPersonalWallet(Wallet personalWallet) {
        this.personalWallet = personalWallet;
    }
}
