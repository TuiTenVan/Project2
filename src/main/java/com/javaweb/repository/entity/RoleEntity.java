package com.javaweb.repository.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<UserRoleEntity> roles = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<UserRoleEntity> getItems() {
        return roles;
    }

    public void setItems(List<UserRoleEntity> items) {
        this.roles = items;
    }
}
