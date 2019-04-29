package com.huayaji.services;

import com.huayaji.dao.UserDao;
import com.huayaji.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(String phone) {
        return userDao.findById(phone);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(String phone) {
        userDao.delete(phone);
    }
}
