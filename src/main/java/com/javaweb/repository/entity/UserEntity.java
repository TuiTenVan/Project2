package com.javaweb.repository.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="username", nullable = false, unique = true)
    private String userName;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="fullName", nullable = false)
    private String fullName;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserRoleEntity> users = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<UserRoleEntity> getItems() {
        return users ;
    }

    public void setItems(List<UserRoleEntity> items) {
        this.users  = items;
    }
}
