package com.javaweb.repository.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="district")

public class DistrictEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
    private List<BuildingEntity> items = new ArrayList<>();

    public List<BuildingEntity> getItems() {
        return items;
    }

    public void setItems(List<BuildingEntity> items) {
        this.items = items;
    }
    
    @Column(name="code")
    private String code;
    @Column(name="name")
    private String name;
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
