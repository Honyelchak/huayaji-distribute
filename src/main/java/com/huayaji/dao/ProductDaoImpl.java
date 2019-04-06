package com.huayaji.dao;

import com.huayaji.entity.Product;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ProductDaoImpl extends HibernateDaoSupport implements ProductDao{

    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void save(Product product) {
        this.getHibernateTemplate().save(product);
    }

    @Override
    public List<Product> findAll() {
        return this.getHibernateTemplate().loadAll(Product.class);
    }

    @Override
    public Product findById(Long id) {
        return this.getHibernateTemplate().get(Product.class, id);
    }

    @Override
    public void update(Product product) {
        this.getHibernateTemplate().update(product);
    }

    @Override
    public void delete(Long id) {
        this.getHibernateTemplate().delete(findById(id));
    }
}
