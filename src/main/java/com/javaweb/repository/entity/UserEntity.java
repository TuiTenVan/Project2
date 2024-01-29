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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "assignmentcustomer",
            joinColumns = @JoinColumn(name = "staffid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "customerid", nullable = false)
    )
    private List<CustomerEntity> customers = new ArrayList<>();

    public List<CustomerEntity> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerEntity> customers) {
        this.customers = customers;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "userid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "roleid", nullable = false)
    )
    private List<RoleEntity> roles = new ArrayList<>();

    public List<RoleEntity> getRoles() {
        return roles;
    }

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<BuildingEntity> buildings = new ArrayList<>();

    public List<BuildingEntity> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<BuildingEntity> buildings) {
        this.buildings = buildings;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

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

}
