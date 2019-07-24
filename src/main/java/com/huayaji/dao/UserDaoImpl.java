package com.huayaji.dao;

import com.huayaji.entity.Order;
import com.huayaji.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public User findById(Long id) {
        return this.getHibernateTemplate().get(User.class, id);
    }

    @Override
    public void update(User user) {
        this.getHibernateTemplate().update(user);
    }

    @Override
    public void delete(Long id) {
        this.getHibernateTemplate().delete(findById(id));
    }

    @Override
    @Transactional
    public List<User> findByPage(Integer page, Integer limit, String search) {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria cri = session.createCriteria(User.class);
        if(search!=null&&search!=""&&!search.equals("")&&!search.equals(null))
            cri.add(Restrictions.like("id",Long.parseLong(search)));
        cri.setFirstResult((page-1)*limit);
        cri.setMaxResults(limit);
        List<User> list = cri.list();
        session.close();
        if(list!=null&&list.size()>0)
            return list;
        return  null;
    }

    @Override
    @Transactional
    public long getCount(String search)
    {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria cri = session.createCriteria(User.class);
        if(search!=null&&search!=""&&!search.equals("")&&!search.equals(null))
            cri.add(Restrictions.like("id",Long.parseLong(search)));
        long count = (long)cri.setProjection(Projections.rowCount()).uniqueResult();
        return count;
    }

}
