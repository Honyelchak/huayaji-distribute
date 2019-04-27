package com.huayaji.entity;

import jdk.Exported;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="user_distribute")
public class Distribute implements Serializable {

    @Id
    private Long id;

    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL) //JPA注释： 一对一 关系
    @JoinColumn(name="user_id",referencedColumnName="id",nullable=false)
    private User user;

    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL) //JPA注释： 一对一 关系
    @JoinColumn(name="product_id",referencedColumnName="id",nullable=false)
    private Product product;

    @Column(name="distribute_time")
    private Timestamp distributeTime;
    @Column(name="distribute_time_type")
    private int distributeTimeType;
    @Column(name="distribute_count_per")
    private int distributeCountPer;
    @Column(name="distribute_balance")
    private int distributeBalance;
    private String comment;

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

    public int getDistributeTimeType() {
        return distributeTimeType;
    }

    public void setDistributeTimeType(int distributeTimeType) {
        this.distributeTimeType = distributeTimeType;
    }

    public int getDistributeCountPer() {
        return distributeCountPer;
    }

    public void setDistributeCountPer(int distributeCountPer) {
        this.distributeCountPer = distributeCountPer;
    }

    public int getDistributeBalance() {
        return distributeBalance;
    }

    public void setDistributeBalance(int distributeBalance) {
        this.distributeBalance = distributeBalance;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
