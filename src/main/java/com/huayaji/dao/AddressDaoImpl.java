package com.huayaji.dao;

import com.huayaji.entity.Address;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class AddressDaoImpl extends HibernateDaoSupport implements AddressDao{

    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void save(Address address) {
        this.getHibernateTemplate().save(address);
    }

    @Override
    public List<Address> findAll() {
        return this.getHibernateTemplate().loadAll(Address.class);
    }

    @Override
    public Address findById(Long id) {
        return this.getHibernateTemplate().get(Address.class, id);
    }

    @Override
    public void update(Address address) {
        this.getHibernateTemplate().update(address);
    }

    @Override
    public void delete(Long id) {
        this.getHibernateTemplate().delete(findById(id));
    }
}
