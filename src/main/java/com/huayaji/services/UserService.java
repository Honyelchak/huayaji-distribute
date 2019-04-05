package com.huayaji.services;

import com.huayaji.entity.User;

import java.util.List;

public interface UserService {

    public void save(User user);
    public List<User> findAll();
    public User findById(Long id);
    public void update(User user);
    public void delete(Long id);
}
