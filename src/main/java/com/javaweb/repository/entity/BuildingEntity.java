package com.javaweb.repository.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="building")
public class BuildingEntity {
	
	@ManyToOne
	@JoinColumn(name="districtid")
	private DistrictEntity district;

	public DistrictEntity getDistrict() {
		return district;
	}

	public void setDistrict(DistrictEntity district) {
		this.district = district;
	}

	@OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
	private List<RentAreaEntity> buildings = new ArrayList<>();

	public List<RentAreaEntity> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<RentAreaEntity> buildings) {
		this.buildings = buildings;
	}

	public List<RentTypeEntity> getRentTypeEntities() {
		return rentTypeEntities;
	}

	public void setRentTypeEntities(List<RentTypeEntity> rentTypeEntities) {
		this.rentTypeEntities = rentTypeEntities;
	}

	public List<RentAreaEntity> getItems() {
		return buildings;
	}
	public void setItems(List<RentAreaEntity> items) {
		this.buildings = items;
	}

	@ManyToMany(mappedBy = "buildings",fetch = FetchType.LAZY)
	private List<RentTypeEntity> rentTypeEntities = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "assignmentbuilding",
			joinColumns = @JoinColumn(name = "buildingid", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "staffid", nullable = false)
	)

	private  List<UserEntity> users = new ArrayList<>();

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	@Id // khóa chính
	@GeneratedValue(strategy = GenerationType.IDENTITY) //tự động tăng
	private Integer id;
	@Column(name="name")
	private String name;
	@Column(name="floorarea")
	private Integer floorArea;

	@Column(name="street")
	private String street;
	@Column(name="ward")
	private String ward;
	@Column(name="numberofbasement")
	private Integer numberOfBasement;
	@Column(name="direction")
	private String direction;
	@Column(name="level")
	private String level;
	@Column(name="rentarea")
	private String rentArea;
	@Column(name="rentprice")
	private Integer rentPrice;
	@Column(name="managername")
	private String managerName;
	@Column(name="managerphonenumber")
	private String managerPhoneNumber;
	@Column(name="servicefee")
	private String serviceFee;
	@Column(name="brokeragefee")
	private String brokerageFee;
	
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
	public Integer getFloorArea() {
		return floorArea;
	}
	public void setFloorArea(Integer floorArea) {
		this.floorArea = floorArea;
	}

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}
	public void setNumberOfBasement(Integer numberOfBasement) {
		this.numberOfBasement = numberOfBasement;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}

	public String getRentArea() {
		return rentArea;
	}

	public void setRentArea(String rentArea) {
		this.rentArea = rentArea;
	}

	public Integer getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(Integer rentPrice) {
		this.rentPrice = rentPrice;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerPhoneNumber() {
		return managerPhoneNumber;
	}
	public void setManagerPhoneNumber(String managerPhoneNumber) {
		this.managerPhoneNumber = managerPhoneNumber;
	}

	public String getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getBrokerageFee() {
		return brokerageFee;
	}
	public void setBrokerageFee(String brokerageFee) {
		this.brokerageFee = brokerageFee;
	}
	
}
