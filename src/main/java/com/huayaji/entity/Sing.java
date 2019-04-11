package com.huayaji.entity;

import javax.persistence.*;
import java.sql.Date;
@Entity
@Table(name="distribute_of_single")
public class Sing {

    @Id
    private int id;
    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL) //JPA注释： 一对一 关系
    @JoinColumn(name="user_id",referencedColumnName="id",nullable=false)
    private User user;
    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL) //JPA注释： 一对一 关系
    @JoinColumn(name="product_id",referencedColumnName="id",nullable=false)
    private Product product;
    private Date distribute_time;//客户待配送日期
    private int distribute_data;//待配送数量
    private String distribute_operation;//配送员配送操作
    private String receive_operation;//客户收货操作
    private  int distribute_status;//某个配送日期（已配送，正在配送, 待配送(三个状态)可用123表示

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

    public int getDistribute_data() {
        return distribute_data;
    }

    public void setDistribute_data(int distribute_data) {
        this.distribute_data = distribute_data;
    }

    public String getDistribute_operation() {
        return distribute_operation;
    }

    public void setDistribute_operation(String distribute_operation) {
        this.distribute_operation = distribute_operation;
    }

    public String getReceive_operation() {
        return receive_operation;
    }

    public void setReceive_operation(String receive_operation) {
        this.receive_operation = receive_operation;
    }

    public int getDistribute_status() {
        return distribute_status;
    }

    public void setDistribute_status(int distribute_status) {
        this.distribute_status = distribute_status;
    }
}
