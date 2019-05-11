package com.huayaji.entity;

import jdk.Exported;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="user_distribute")
public class Distribute implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.REFRESH) //JPA注释： 一对一 关系
    @JoinColumn(name="user_id",referencedColumnName="id",nullable=false)
    private User user;

    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.REFRESH) //JPA注释： 一对一 关系
    @JoinColumn(name="product_id",referencedColumnName="id",nullable=false)
    private Product product;

    @Column(name="distribute_time")
    private Timestamp distributeTime;
    @Column(name="distribute_time_type")
    private String  distributeTimeType;
    @Column(name="distribute_count_per")
    private int distributeCountPer;
    @Column(name="distribute_balance")
    private double distributeBalance;
    private String comment1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Timestamp getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(Timestamp distributeTime) {
        this.distributeTime = distributeTime;
    }

    public String  getDistributeTimeType() {
        return distributeTimeType;
    }

    public void setDistributeTimeType(String distributeTimeType) {
        this.distributeTimeType = distributeTimeType;
    }

    public int getDistributeCountPer() {
        return distributeCountPer;
    }

    public void setDistributeCountPer(int distributeCountPer) {
        this.distributeCountPer = distributeCountPer;
    }

    public double getDistributeBalance() {
        return distributeBalance;
    }

    public void setDistributeBalance(double distributeBalance) {
        this.distributeBalance = distributeBalance;
    }

    public String getComment() {
        return comment1;
    }

    public void setComment(String comment) {
        this.comment1 = comment;
    }
}
