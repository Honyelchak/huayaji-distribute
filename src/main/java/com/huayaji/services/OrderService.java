package com.huayaji.services;


import com.huayaji.entity.Order;

import java.util.List;

public interface OrderService {

    public void save(Order user);
    public List<Order> findAll();
    public Order findById(Long id);
    public void update(Order user);
    public void delete(Long id);

    void update(String id, String totalMoney, String distributeTime, String distributeType, String count);
}
