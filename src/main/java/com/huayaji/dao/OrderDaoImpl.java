package com.huayaji.dao;


import com.huayaji.entity.Order;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class OrderDaoImpl extends HibernateDaoSupport implements OrderDao{

    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }
    @Override
    public void save(Order user) {
        this.getHibernateTemplate().save(user);
    }

    @Override
    public List<Order> findAll() {
        return this.getHibernateTemplate().loadAll(Order.class);
    }

    @Override
    public Order findById(Long id) {
        return this.getHibernateTemplate().get(Order.class, id);
    }

    @Override
    public void update(Order user) {
        this.getHibernateTemplate().update(user);
    }

    @Override
    public void delete(Long id) {
        this.getHibernateTemplate().delete(findById(id));
    }

}
