package com.huayaji.dao;


import com.huayaji.entity.User;

import java.util.List;

public interface UserDao {

    public void save(User user);
    public List<User> findAll();
    public User findById(String phone);
    public void update(User user);
    public void delete(String phone);
}
