package com.huayaji.dao;

import com.huayaji.entity.Product;
import com.huayaji.entity.User;

import java.util.List;

public interface ProductDao {

    public void save(Product product);
    public List<Product> findAll();
    public Product findById(Long id);
    public void update(Product product);
    public void delete(Long id);

}
