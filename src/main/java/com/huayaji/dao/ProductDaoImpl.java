package com.huayaji.dao;

import com.huayaji.entity.Product;
import com.huayaji.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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

    @Override
    public List<Product> findByPage(Integer page, Integer limit, String search) {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria cri = session.createCriteria(Product.class);
        if(search!=null&&search!=""&&!search.equals("")&&!search.equals(null))
            cri.add(Restrictions.like("user.id",Long.parseLong(search)));
        cri.setFirstResult((page-1)*limit);
        cri.setMaxResults(limit);
        List<Product> list = cri.list();
        session.close();
        if(list!=null&&list.size()>0)
            return list;
        return  null;
    }

    @Override
    public long getCount(String search)
    {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria cri = session.createCriteria(Product.class);
        if(search!=null&&search!=""&&!search.equals("")&&!search.equals(null))
            cri.add(Restrictions.like("user.id",Long.parseLong(search)));
        long count = (long)cri.setProjection(Projections.rowCount()).uniqueResult();
        return count;
    }
}
