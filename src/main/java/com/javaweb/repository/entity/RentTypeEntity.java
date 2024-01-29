package com.javaweb.repository.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="renttype")
public class RentTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer buildingId;
    @Column
    private Integer renttypeId;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "buildingrenttype",
            joinColumns = @JoinColumn(name = "renttypeid", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "buildingid", nullable = false)
    )
    private List<BuildingEntity> buildings = new ArrayList<>();

    public List<BuildingEntity> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<BuildingEntity> buildings) {
        this.buildings = buildings;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getRenttypeId() {
        return renttypeId;
    }

    public void setRenttypeId(Integer renttypeId) {
        this.renttypeId = renttypeId;
    }
}
