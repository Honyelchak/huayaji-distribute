package com.huayaji.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user_address")
public class Address implements Serializable {

    @Id
    private Long id;
    @Column(name="user_id")
    private Long userId;
    private String province;
    private String city;
    private String county;

    @Column(name="detail_address")
    private String detailAddress;
    private String apartment;
    @Column(name="building_no")
    private int buildingNo;
    @Column(name="house_no")
    private int houseNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public int getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(int buildingNo) {
        this.buildingNo = buildingNo;
    }

    public int getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(int houseNo) {
        this.houseNo = houseNo;
    }

    public Address(Long id, Long userId, String province, String city, String county, String detailAddress, String apartment, int buildingNo, int houseNo) {
        this.id = id;
        this.userId = userId;
        this.province = province;
        this.city = city;
        this.county = county;
        this.detailAddress = detailAddress;
        this.apartment = apartment;
        this.buildingNo = buildingNo;
        this.houseNo = houseNo;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", userId=" + userId +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", apartment='" + apartment + '\'' +
                ", buildingNo=" + buildingNo +
                ", houseNo=" + houseNo +
                '}';
    }

    public Address() {
    }


}
