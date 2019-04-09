package com.huayaji.dao;

import com.huayaji.entity.Distribute;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class DistributeDaoImpl extends HibernateDaoSupport implements DistributeDao{

    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void save(Distribute distribute) {
        this.getHibernateTemplate().save(distribute);
    }

    @Override
    public List<Distribute> findAll() {
        return this.getHibernateTemplate().loadAll(Distribute.class);
    }

    @Override
    public Distribute findById(Long id) {
        return this.getHibernateTemplate().get(Distribute.class, id);
    }

    @Override
    public void update(Distribute distribute) {
        this.getHibernateTemplate().update(distribute);
    }

    @Override
    public void delete(Long id) {
        this.getHibernateTemplate().delete(findById(id));
    }
}
