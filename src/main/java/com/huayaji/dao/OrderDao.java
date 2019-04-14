package com.huayaji.dao;



import com.huayaji.entity.Order;

import java.util.List;

public interface OrderDao {

    public void save(Order user);
    public List<Order> findAll();
    public Order findById(Long id);
    public void update(Order user);
    public void delete(Long id);
}
