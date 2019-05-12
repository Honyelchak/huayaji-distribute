package com.huayaji.services;

import com.huayaji.entity.User;

import java.util.List;

public interface UserService {

    public long getCount(String search);
    public void save(User user);
    public List<User> findAll();
    public User findById(Long phone);
    public void update(User user);
    public void delete(Long phone);
    public List<User> findByPage(Integer page, Integer limit, String search);
}
