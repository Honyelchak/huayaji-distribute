package com.huayaji.services;

import com.huayaji.entity.Product;

import java.util.List;

public interface ProductService {

    public void save(Product product);
    public List<Product> findAll();
    public Product findById(Long id);
    public void update(Product product);
    public void delete(Long id);

}
