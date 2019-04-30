package com.huayaji.dao;

import com.huayaji.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class UserDaoImpl extends HibernateDaoSupport implements UserDao{

    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }
    @Override
    public void save(User user) {
        this.getHibernateTemplate().save(user);
    }

    @Override
    public List<User> findAll() {
        return this.getHibernateTemplate().loadAll(User.class);
    }

    @Override
    public User findById(Long phone) {
        return this.getHibernateTemplate().get(User.class, phone);
    }

    @Override
    public void update(User user) {
        this.getHibernateTemplate().update(user);
    }

    @Override
    public void delete(Long phone) {
        this.getHibernateTemplate().delete(findById(phone));
    }

}
