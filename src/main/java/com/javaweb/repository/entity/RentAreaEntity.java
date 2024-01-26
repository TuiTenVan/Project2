package com.javaweb.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "rentarea")
public class RentAreaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "buildingid")
    private BuildingEntity building;

    public BuildingEntity getBuilding() {
        return building;
    }

    public void setBuilding(BuildingEntity building) {
        this.building = building;
    }

    @Column(name = "value")
    private Integer value;
//    @Column(name = "buildingid")
//    private Integer buildingId;
    


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

//    public Integer getBuildingId() {
//        return buildingId;
//    }
//
//    public void setBuildingId(Integer buildingId) {
//        this.buildingId = buildingId;
//    }
}
