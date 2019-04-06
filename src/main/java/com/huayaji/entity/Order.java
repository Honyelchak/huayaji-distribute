package com.huayaji.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="order")
public class Order implements Serializable {

    @Id
    private Long id;
    private String user_id;
    @OneToOne
    @JoinColumn(name="address_id")
    private Address address;
    @OneToOne
    @JoinColumn(name="product_id")
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



}
