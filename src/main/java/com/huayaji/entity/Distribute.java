package com.huayaji.entity;

import javax.persistence.*;
import java.sql.Date;
@Entity
@Table(name = "user_distribute")
public class Distribute {
    private int id;
    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL) //JPA注释： 一对一 关系
    @JoinColumn(name="user_id",referencedColumnName="id",nullable=false)
    private User user;
    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL) //JPA注释： 一对一 关系
    @JoinColumn(name="product_id",referencedColumnName="id",nullable=false)
    private Product product;
    private Date distribute_time;
    private int distribute_count_per;//每次配送数量
    private int distribute_balance;
    private String comment;//备注

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getDistribute_time() {
        return distribute_time;
    }

    public void setDistribute_time(Date distribute_time) {
        this.distribute_time = distribute_time;
    }

    public int getDistribute_count_per() {
        return distribute_count_per;
    }

    public void setDistribute_count_per(int distribute_count_per) {
        this.distribute_count_per = distribute_count_per;
    }

    public int getDistribute_balance() {
        return distribute_balance;
    }

    public void setDistribute_balance(int distribute_balance) {
        this.distribute_balance = distribute_balance;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
