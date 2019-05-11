package com.huayaji.dao;

import com.huayaji.entity.Sing;
import com.huayaji.entity.TemporarySing;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class SingDaoImpl extends HibernateDaoSupport implements SingDao{

    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void saveTemporary(TemporarySing sing) {
        this.getHibernateTemplate().save(sing);
    }
    @Override
    public void save(Sing sing) {
        this.getHibernateTemplate().save(sing);
    }

    @Override
    public List<TemporarySing> findTemporaryAll() {
        return this.getHibernateTemplate().loadAll(TemporarySing.class);
    }
    @Override
    public List<Sing> findAll() {
        return this.getHibernateTemplate().loadAll(Sing.class);
    }
    @Override
    public Sing findById(Long id) {
        return this.getHibernateTemplate().get(Sing.class, id);
    }
    @Override
    public TemporarySing findTemporaryById(Long id) {
        return this.getHibernateTemplate().get(TemporarySing.class, id);
    }
    @Override
    public void updateTemporary(TemporarySing sing) {
        this.getHibernateTemplate().update(sing);
    }
    @Override
    public void update(Sing sing) {
        this.getHibernateTemplate().update(sing);
    }

    @Override
    public void deleteTemporary(Long id) {
        this.getHibernateTemplate().delete(findTemporaryById(id));
    }
    @Override
    public void delete(Long id) {
        this.getHibernateTemplate().delete(findById(id));
    }
}
