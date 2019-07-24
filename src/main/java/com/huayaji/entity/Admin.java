package com.huayaji.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="admin")
public class Admin  implements Serializable {

    @Id
    private Long id;
    @Column(name="userName")
    private String userName;
    @Column(name="passWord")
    private String passWord;
    private String phone;
    @Column(name="add_time")
    private Timestamp addTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", phone='" + phone + '\'' +
                ", addTime=" + addTime +
                '}';
    }

    public Admin(Long id, String userName, String passWord, String phone, Timestamp addTime) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.phone = phone;
        this.addTime = addTime;
    }

    public Admin() {
    }
}
