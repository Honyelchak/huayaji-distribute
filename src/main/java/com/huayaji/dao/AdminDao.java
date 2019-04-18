package com.huayaji.dao;

import com.huayaji.entity.Admin;

import java.util.List;

public interface AdminDao  {

    public void save(Admin admin);

    public List<Admin> findAll();

    public Admin findById(Long id);

    public void update(Admin admin);

    public void delete(Long id);

    public List<Admin> login(String userName, String passWord);

    public List<Admin> findByUserName(String userName);
}
