package com.huayaji.entity;


import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user_data")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String real_name;
    private String sex;
    private int age;
    private long service_no;
    private String note;
    @Column(name="distribute_balance")
    private int distribute_balance;


    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL) //JPA注释： 一对一 关系
    @JoinColumn(name="address_id", referencedColumnName="id", nullable=true)
    @NotFound(action=NotFoundAction.IGNORE)
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public long getService_no() {
        return service_no;
    }

    public void setService_no(long service_no) {
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

    public User(String id, String name, String real_name, String sex, int age, long service_no, String note, int distribute_balance) {
        this.name = name;
        this.real_name = real_name;
        this.id = id;
        this.sex = sex;
        this.age = age;
        this.service_no = service_no;
        this.note = note;
        this.distribute_balance = distribute_balance;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "," + address.toString();
    }
}
