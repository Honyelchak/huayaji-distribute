package com.huayaji.dao;

import com.huayaji.entity.Distribute;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;


import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public class DistributeDaoImpl extends HibernateDaoSupport implements DistributeDao {

    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory) {
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
        return this.getHibernateTemplate().get(Distribute.class, Integer.parseInt(id.toString()));
    }

    @Override
    public void update(Distribute distribute) {
        this.getHibernateTemplate().update(distribute);
    }

    @Override
    public void delete(Long id) {
        this.getHibernateTemplate().delete(findById(id));
    }



    @Override
    public void update(String id, String distributeBalance, String distributeCountPer, String distributeTimeType, String distributeTime, String comment) {
        Distribute distribute=this.getHibernateTemplate().get(Distribute.class,Long.parseLong(id));
        DateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        try {
            distribute.setDistributeTime(new Timestamp( format.parse(distributeTime).getTime()));
            distribute.setDistributeBalance(Double.parseDouble(distributeBalance));
            distribute.setDistributeCountPer(Integer.parseInt(distributeCountPer));
            distribute.setDistributeTimeType(distributeTimeType);
            distribute.setComment(comment);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.getHibernateTemplate().update(distribute);
    }
}
