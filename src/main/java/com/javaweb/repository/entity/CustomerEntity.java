package com.javaweb.repository.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String fullname;
    @Column
    private String phone;
    @Column
    private String email;
    @ManyToMany(mappedBy = "customers",fetch = FetchType.LAZY)
    private List<UserEntity> users = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "transaction",
            joinColumns = @JoinColumn(name = "customerid"),
            inverseJoinColumns = @JoinColumn(name = "type")
    )
    private List<TransactionTypeEntity> transactions = new ArrayList<>();

    public List<TransactionTypeEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionTypeEntity> transactions) {
        this.transactions = transactions;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
