package com.javaweb.builder;

import java.util.ArrayList;
import java.util.List;

public class BuildingSearchBuilder {
    private String name;
    private Integer floorArea;
    private Integer numberOfBasement;
    private String districtId;
    private String ward;
    private String street;
    private String managerName;
    private String managerPhoneNumber;
    private String direction;
    private String level;
    private List<String> typeCode = new ArrayList<>();
    private Integer rentPriceFrom;
    private Integer rentPriceTo;
    private String areaFrom;
    private String areaTo;
    private Integer staffId;

    private BuildingSearchBuilder(Builder builder){
        this.name = builder.name;
        this.floorArea = builder.floorArea;
        this.ward = builder.ward;
        this.street = builder.street;
        this.districtId = builder.districtId;
        this.numberOfBasement = builder.numberOfBasement;
        this.level = builder.level;
        this.direction = builder.direction;
        this.typeCode = builder.typeCode;
        this.managerName = builder.managerName;
        this.managerPhoneNumber = builder.managerPhoneNumber;
        this.rentPriceFrom = builder.rentPriceFrom;
        this.rentPriceTo = builder.rentPriceTo;
        this.areaFrom = builder.areaFrom;
        this.areaTo = builder.areaTo;
        this.staffId = builder.staffId;
    }
    public String getName() {
        return name;
    }

    public Integer getFloorArea() {
        return floorArea;
    }

    public Integer getNumberOfBasement() {
        return numberOfBasement;
    }

    public String getDistrictId() {
        return districtId;
    }

    public String getWard() {
        return ward;
    }

    public String getStreet() {
        return street;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getManagerPhoneNumber() {
        return managerPhoneNumber;
    }

    public String getDirection() {
        return direction;
    }

    public String getLevel() {
        return level;
    }

    public List<String> getTypeCode() {
        return typeCode;
    }

    public Integer getRentPriceFrom() {
        return rentPriceFrom;
    }

    public Integer getRentPriceTo() {
        return rentPriceTo;
    }

    public String getAreaFrom() {
        return areaFrom;
    }

    public String getAreaTo() {
        return areaTo;
    }

    public Integer getStaffId() {
        return staffId;
    }
    public static class Builder{
        private String name;
        private Integer floorArea;
        private Integer numberOfBasement;
        private String districtId;
        private String ward;
        private String street;
        private String managerName;
        private String managerPhoneNumber;
        private String direction;
        private String level;
        private List<String> typeCode = new ArrayList<>();
        private Integer rentPriceFrom;
        private Integer rentPriceTo;
        private String areaFrom;
        private String areaTo;
        private Integer staffId;

        public void setName(String name) {
            this.name = name;
        }

        public void setFloorArea(Integer floorArea) {
            this.floorArea = floorArea;
        }

        public void setNumberOfBasement(Integer numberOfBasement) {
            this.numberOfBasement = numberOfBasement;
        }

        public void setDistrictId(String districtId) {
            this.districtId = districtId;
        }

        public void setWard(String ward) {
            this.ward = ward;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public void setManagerName(String managerName) {
            this.managerName = managerName;
        }

        public void setManagerPhoneNumber(String managerPhoneNumber) {
            this.managerPhoneNumber = managerPhoneNumber;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public void setTypeCode(List<String> typeCode) {
            this.typeCode = typeCode;
        }

        public void setRentPriceFrom(Integer rentPriceFrom) {
            this.rentPriceFrom = rentPriceFrom;
        }

        public void setRentPriceTo(Integer rentPriceTo) {
            this.rentPriceTo = rentPriceTo;
        }

        public void setAreaFrom(String areaFrom) {
            this.areaFrom = areaFrom;
        }

        public void setAreaTo(String areaTo) {
            this.areaTo = areaTo;
        }

        public void setStaffId(Integer staffId) {
            this.staffId = staffId;
        }
        public BuildingSearchBuilder build(){
            return new BuildingSearchBuilder(this);
        }
    }
}
