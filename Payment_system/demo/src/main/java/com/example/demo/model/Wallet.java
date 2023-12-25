package com.example.demo.model;
import  com.example.demo.model.User;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "wallet_details")

public class Wallet{
  @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long walletid;
    @OneToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    private User user;
    private BigDecimal walletBalance;
    private BigDecimal amountAvailableOnOffline;
    @ElementCollection
    @CollectionTable(name="codes_of_wallet",joinColumns = @JoinColumn(name = "walletid"))
    @Column(name="codes_for_transaction")
    private Set<String> codes_for_transaction = new HashSet<>();

    public Wallet()
    {
        this.walletBalance= new BigDecimal(0);
        this.amountAvailableOnOffline= new BigDecimal(0);
    }
    public Wallet(Long walletid,User user)
    {
        this.walletid=walletid;
        this.user=user;
        this.walletBalance= new BigDecimal(0);
        this.amountAvailableOnOffline= new BigDecimal(0);
    }
    public Wallet(User user) {
        this.user = user;
        this.walletBalance= new BigDecimal(0);
        this.amountAvailableOnOffline= new BigDecimal(0);
    }
    public Wallet(long walletid, User user, BigDecimal walletBalance, BigDecimal amountAvailableOnOffline) {
        this.walletid = walletid;
        this.user = user;
        this.walletBalance = walletBalance;
        this.amountAvailableOnOffline = amountAvailableOnOffline;
    }
    public Wallet(long walletid, User user, BigDecimal walletBalance, BigDecimal amountAvailableOnOffline, Set<String> codes_for_transaction) {
        this.walletid = walletid;
        this.user = user;
        this.walletBalance = walletBalance;
        this.amountAvailableOnOffline = amountAvailableOnOffline;
        this.codes_for_transaction = codes_for_transaction;
    }

    public Wallet(Long walletId) {
        this.walletid=walletId;
        this.walletBalance= new BigDecimal(0);
        this.amountAvailableOnOffline= new BigDecimal(0);
    }

    public long getWalletid() {
        return walletid != null ? walletid : 0L;
    }

    public void setWalletid(long walletid) {
        this.walletid = walletid;
    }

    public Set<String> getCodes_for_transaction() {
        return codes_for_transaction;
    }

    public void setCodes_for_transaction(Set<String> codes_for_transaction) {
        this.codes_for_transaction = codes_for_transaction;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }

    public BigDecimal getAmountAvailableOnOffline() {
        return amountAvailableOnOffline;
    }

    public void setAmountAvailableOnOffline(BigDecimal amountAvailableOnOffline) {
        this.amountAvailableOnOffline = amountAvailableOnOffline;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "walletid=" + walletid +
                ", user=" + user +
                ", walletBalance=" + walletBalance +
                ", amountAvailableOnOffline=" + amountAvailableOnOffline +
                ", codes_for_transaction=" + codes_for_transaction +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return walletid == wallet.walletid && Objects.equals(user, wallet.user) && Objects.equals(walletBalance, wallet.walletBalance) && Objects.equals(amountAvailableOnOffline, wallet.amountAvailableOnOffline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletid, user, walletBalance, amountAvailableOnOffline);
    }
}
