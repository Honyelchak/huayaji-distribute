package com.huayaji.dao;

import com.huayaji.entity.Address;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class AddressDaoImpl extends HibernateDaoSupport implements AddressDao{

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
