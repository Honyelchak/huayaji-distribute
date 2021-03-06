package com.huayaji.dao;

import com.huayaji.entity.Distribute;

import com.huayaji.entity.Order;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
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
        return this.getHibernateTemplate().get(Distribute.class, id.toString());
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
    @Transactional
    public void update(String days,String userid ,String distributeCountPer) {
        Distribute distribute=findByUseridAndProduct(userid,"1");
        DateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        try {
            distribute.setDistributeLastTime(new Timestamp( distribute.getDistributeTime().getTime() +24*60*60*1000*Integer.parseInt(days)));
            distribute.setDistributeCountPer(Integer.parseInt(distributeCountPer));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.getHibernateTemplate().update(distribute);
    }

    @Override
    @Transactional
    public List<Distribute> findByUserid(String id) {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria cri = session.createCriteria(Distribute.class);
        cri.add(Restrictions.eq("user.id", Long.parseLong(id)));

        List<Distribute> list = cri.list();
        session.close();
        return list;
    }

    @Override
    @Transactional
    public List<Distribute> findByPage(Integer page, Integer limit, String search) {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria cri = session.createCriteria(Distribute.class);
        if(search!=null&&search!=""&&!search.equals("")&&!search.equals(null))
            cri.add(Restrictions.like("user.id",Long.parseLong(search)));
        cri.setFirstResult((page-1)*limit);
        cri.setMaxResults(limit);
        List<Distribute> list = cri.list();
        session.close();
        if(list!=null&&list.size()>0)
            return list;
        return  null;
    }

    @Override
    @Transactional
    public long getCount(String search) {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria cri = session.createCriteria(Distribute.class);
        if(search!=null&&search!=""&&!search.equals("")&&!search.equals(null))
            cri.add(Restrictions.like("user.id",Long.parseLong(search)));
        long count =(long)cri.setProjection(Projections.rowCount()).uniqueResult();


        return count;
    }

    @Override
    @Transactional
    public Distribute findByUseridAndProduct(String userid, String productid) {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria cri = session.createCriteria(Distribute.class);
        cri.add(Restrictions.eq("user.id",Long.parseLong(userid)));
        cri.add(Restrictions.eq("product.id",Long.parseLong(productid)));

        List<Distribute> list = cri.list();
        session.close();
        if(list!=null&&list.size()>0)
            return list.get(0);
        return null;
    }
}
