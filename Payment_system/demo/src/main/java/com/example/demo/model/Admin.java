package com.example.demo.model;


import javax.persistence.*;

@Entity
@Table(name = "admin_details")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;
    private String name;
    @Enumerated(EnumType.STRING)
    UserType userType;
    public Admin()
    {
        userType=UserType.admin;
    }

    public Admin(Long adminId, String name) {
        this.adminId = adminId;
        this.name = name;
        userType=UserType.admin;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
