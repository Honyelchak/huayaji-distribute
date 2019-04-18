package com.huayaji.services;

import com.huayaji.dao.AdminDao;
import com.huayaji.entity.Admin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    AdminDao adminDao;
    @Override
    public void save(Admin admin) {
        adminDao.save(admin);
    }

    @Override
    public List<Admin> findAll() {
        return adminDao.findAll();
    }

    @Override
    public Admin findById(Long id) {
        return adminDao.findById(id);
    }

    @Override
    public void update(Admin admin) {
        adminDao.update(admin);
    }

    @Override
    public void delete(Long id) {
        adminDao.delete(id);
    }

    @Override
    public Admin login(String userName, String passWord) {
        List<Admin> adminList = adminDao.login(userName, passWord);
        if (adminList.size() >= 1) {
            return adminList.get(0);
        }
        return null;
    }

    @Override
    public Admin findByUserName(String userName) {
        List<Admin> adminList = adminDao.findByUserName(userName);
        if (adminList.size() >= 1) {
            return adminList.get(0);
        }
        return null;
    }
}
