package com.huayaji.dao;


import com.huayaji.entity.Order;
import com.huayaji.entity.User;

import java.util.List;

public interface UserDao {

    public void save(User user);
    public List<User> findAll();
    public User findById(Long id);
    public void update(User user);
    public void delete(Long id);
    public List<User> findByPage(Integer page, Integer limit, String search);
    public long getCount(String search);
}
