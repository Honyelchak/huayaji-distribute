package com.huayaji.services;

import com.huayaji.entity.Product;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    @Override
    public void save(Product product) {

    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Product findById(Long id) {
        return null;
    }

    @Override
    public void update(Product product) {

    }

    @Override
    public void delete(Long id) {

    }
}
