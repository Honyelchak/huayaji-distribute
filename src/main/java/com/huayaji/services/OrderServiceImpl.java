package com.huayaji.services;


import com.huayaji.dao.OrderDao;
import com.huayaji.entity.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Override
    public void save(Order user) {
        orderDao.save(user);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderDao.findById(id);
    }

    @Override
    public void update(Order user) {
        orderDao.update(user);
    }

    @Override
    public void delete(Long id) {
        orderDao.delete(id);
    }

    @Override
    public Order findByUseridAndProductid(long  userid,long productid) {
        return orderDao.findByUseridAndProductid(userid,productid);
    }

    @Override
    public void update(String id, String totalMoney, String distributeTime, String distributeType, String count) {
        orderDao.update(id,totalMoney,distributeTime,distributeType,count);
    }
}
