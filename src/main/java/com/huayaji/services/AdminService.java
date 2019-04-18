package com.huayaji.services;

import com.huayaji.entity.Admin;

import java.util.List;

public interface AdminService {
    public void save(Admin admin);
    public List<Admin> findAll();
    public Admin findById(Long id);
    public void update(Admin admin);
    public void delete(Long id);
    public Admin login(String userName, String passWord);
    public Admin findByUserName(String userName);
}
