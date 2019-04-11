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

    @Column(name="address")
    private String detailAddress;
    private String apartment;
    private int building_no;
    private int house_no;

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

    public int getBuilding_no() {
        return building_no;
    }

    public void setBuilding_no(int building_no) {
        this.building_no = building_no;
    }

    public int getHouse_no() {
        return house_no;
    }

    public void setHouse_no(int house_no) {
        this.house_no = house_no;
    }

    public Address(Long id, Long userId, String province, String city, String county, String detailAddress, String apartment, int building_no, int house_no) {
        this.id = id;
        this.userId = userId;
        this.province = province;
        this.city = city;
        this.county = county;
        this.detailAddress = detailAddress;
        this.apartment = apartment;
        this.building_no = building_no;
        this.house_no = house_no;
    }

    public Address() {
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
                ", building_no=" + building_no +
                ", house_no=" + house_no +
                '}';
    }
}
