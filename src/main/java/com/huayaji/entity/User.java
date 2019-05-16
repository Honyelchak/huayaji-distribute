package com.huayaji.entity;


import com.huayaji.util.BeanNote;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user_data")
public class User implements Serializable {

    @Id
    @BeanNote(name="手机号",width = 30)
    private Long id;
    @BeanNote(name="微信号")
    private String name;
    @BeanNote(name="真实姓名")
    private String real_name;
    @BeanNote(name="性别")
    private String sex;
    @BeanNote(name="年龄")
    private int age;
    @BeanNote(name="客服微信号")
    private String service_no;
    @BeanNote(name="备注")
    private String note;
    @Column(name="distribute_balance")
    @BeanNote(name="配送余额")
    private int distribute_balance;
    @BeanNote(name="省")
    private String province;
    @BeanNote(name="市")
    private String city;
    @BeanNote(name="县")
    private String county;

    @Column(name="detail_address")
    @BeanNote(name="详细地址")
    private String detailAddress;
    @BeanNote(name="小区名称")
    private String apartment;
    @Column(name="building_no")
    @BeanNote(name="楼号")
    private int buildingNo;
    @Column(name="house_no")
    @BeanNote(name="房间号")
    private int houseNo;

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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getService_no() {
        return service_no;
    }

    public void setService_no(String service_no) {
        this.service_no = service_no;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getDistribute_balance() {
        return distribute_balance;
    }

    public void setDistribute_balance(int distribute_balance) {
        this.distribute_balance = distribute_balance;
    }

    public User(Long id, String name, String real_name, String sex, int age, String service_no, String note, int distribute_balance, String province, String city, String county, String detailAddress, String apartment, int buildingNo, int houseNo) {
        this.id = id;
        this.name = name;
        this.real_name = real_name;
        this.sex = sex;
        this.age = age;
        this.service_no = service_no;
        this.note = note;
        this.distribute_balance = distribute_balance;
        this.province = province;
        this.city = city;
        this.county = county;
        this.detailAddress = detailAddress;
        this.apartment = apartment;
        this.buildingNo = buildingNo;
        this.houseNo = houseNo;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", real_name='" + real_name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", service_no=" + service_no +
                ", note='" + note + '\'' +
                ", distribute_balance=" + distribute_balance +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", apartment='" + apartment + '\'' +
                ", buildingNo=" + buildingNo +
                ", houseNo=" + houseNo +
                '}';
    }
}
