package com.huayaji.dao;

import com.huayaji.entity.Admin;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class AdminDaoImpl extends HibernateDaoSupport implements AdminDao {

    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void save(Admin admin) {
        this.getHibernateTemplate().save(admin);
    }

    @Override
    public List<Admin> findAll() {
        return this.getHibernateTemplate().loadAll(Admin.class);
    }

    @Override
    public Admin findById(Long id) {
        return this.getHibernateTemplate().get(Admin.class, id);
    }

    @Override
    public void update(Admin admin) {
        this.getHibernateTemplate().update(admin);
    }

    @Override
    public void delete(Long id) {
        this.getHibernateTemplate().delete(findById(id));
    }

    @Override
    public List<Admin> login(String userName, String passWord) {
        return (List<Admin>) this.getHibernateTemplate().find("from Admin where user_name = ? and password = ?", new String[]{userName, passWord});
    }
    @Override
    public List<Admin> findByUserName(String userName){
        return (List<Admin>) this.getHibernateTemplate().find("from Admin where user_name = ?", userName);
    }
}
