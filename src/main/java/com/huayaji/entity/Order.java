package com.huayaji.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="order_data")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.REFRESH ) //JPA注释： 一对一 关系
    @JoinColumn(name="user_id", referencedColumnName="id", nullable=true)
//    @NotFound(action=NotFoundAction.IGNORE)
    private User user;

    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.REFRESH) //JPA注释： 一对一 关系
    @JoinColumn(name="product_id", referencedColumnName="id", nullable=true)
//    @NotFound(action=NotFoundAction.IGNORE)
    private Product product;
    @Column(name="total_money")
    private double totalMoney;
    private int count;
    // 下单时间
    @Column(name="order_time")
    private Timestamp orderTime;
    // 第一次配送时间
    @Column(name="distribute_time")
    private Timestamp distributeTime;
    // 配送类型
    @Column(name="distribute_type")
    private int distributeType;

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

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public Timestamp getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(Timestamp distributeTime) {
        this.distributeTime = distributeTime;
    }

    public int getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(int distributeType) {
        this.distributeType = distributeType;
    }
}
