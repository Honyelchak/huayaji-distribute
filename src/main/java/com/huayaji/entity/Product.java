package com.huayaji.entity;

import com.huayaji.util.BeanNote;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @BeanNote(name="产品名称")
    private String name;
    private String type;
    private double price;
    @Column(name="distribute_type")
    private String distributeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(String distributeType) {
        this.distributeType = distributeType;
    }

    public Product() {
    }

    public Product(Long id, String name, String type, double price, String distributeType) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.distributeType = distributeType;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", distributeType='" + distributeType + '\'' +
                '}';
    }
}
