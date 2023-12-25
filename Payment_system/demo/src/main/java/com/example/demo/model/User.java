package com.example.demo.model;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user_details")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userid;
    /*private String userName;
    private String password;*/
    private String secret_code;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wallet wallet;
    private Boolean userStatus;
    private Boolean userEnrollmentStatus;
    private Boolean userApprovalStatus;
    private Double userLatitude;
    private Double userLongitude;
    @ElementCollection
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    private Set<String> authorities;
    @Enumerated(EnumType.STRING)
    UserType userType;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public User()
    {
        userStatus=true;
        userEnrollmentStatus=false;
        userApprovalStatus=false;
        userType=UserType.user;
    }
    public User(int userid, String secret_code, Boolean userStatus, Boolean userEnrollmentStatus, Boolean userApprovalStatus, Double userLatitude, Double userLongitude) {
        this.userid=userid;
        this.secret_code = secret_code;
        this.wallet = wallet;
        this.userStatus = userStatus;
        this.userEnrollmentStatus = userEnrollmentStatus;
        this.userApprovalStatus = userApprovalStatus;
        this.userLatitude = userLatitude;
        userType=UserType.user;
        this.userLongitude = userLongitude;
    }
    public int getId( ){
        return userid;
    }
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public String getSecret_code() {
        return secret_code;
    }

    public void setSecret_code(String secret_code) {
        this.secret_code = secret_code;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Boolean getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
    }

    public Boolean getUserEnrollmentStatus() {
        return userEnrollmentStatus;
    }

    public void setUserEnrollmentStatus(Boolean userEnrollmentStatus) {
        this.userEnrollmentStatus = userEnrollmentStatus;
    }

    public Boolean getUserApprovalStatus() {
        return userApprovalStatus;
    }

    public void setUserApprovalStatus(Boolean userApprovalStatus) {
        this.userApprovalStatus = userApprovalStatus;
    }

    public Double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(Double userLatitude) {
        this.userLatitude = userLatitude;
    }

    public Double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(Double userLongitude) {
        this.userLongitude = userLongitude;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userType.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userid == user.userid && Objects.equals(secret_code, user.secret_code) && Objects.equals(userStatus, user.userStatus) && Objects.equals(userEnrollmentStatus, user.userEnrollmentStatus) && Objects.equals(userApprovalStatus, user.userApprovalStatus) && Objects.equals(userLatitude, user.userLatitude) && Objects.equals(userLongitude, user.userLongitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, secret_code, userStatus, userEnrollmentStatus, userApprovalStatus, userLatitude, userLongitude);
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", secret_code='" + secret_code + '\'' +
                ", userStatus=" + userStatus +
                ", userEnrollmentStatus=" + userEnrollmentStatus +
                ", userApprovalStatus=" + userApprovalStatus +
                ", userLatitude=" + userLatitude +
                ", userLongitude=" + userLongitude +
                '}';
    }
}
