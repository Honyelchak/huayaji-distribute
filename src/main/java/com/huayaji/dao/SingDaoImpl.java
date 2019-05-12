package com.huayaji.dao;

import com.huayaji.entity.Sing;
import com.huayaji.entity.TemporarySing;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Statement;
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
        return this.getHibernateTemplate().get(TemporarySing.class, Integer.parseInt(id.toString()));
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

    public void deleteAllTem() {
        Session session=getHibernateTemplate().getSessionFactory().openSession();
        String sql="truncate table distribute_temporary "; //sql删除语句
        Transaction ts=session.beginTransaction();//开始执行
        //获取connection,执行静态SQL
        session.createSQLQuery(sql).executeUpdate();
        ts.commit();
        session.close();
    }

    @Override
    public Sing findByUseridAndProductidAnddate(TemporarySing t) {

        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria cri = session.createCriteria(Sing.class);
        cri.add(Restrictions.eq("user.id", t.getUser().getId()));
        cri.add(Restrictions.eq("product.id",t.getProduct().getId()));
        cri.add(Restrictions.like("distribute_time",t.getDistribute_time()));
        List<Sing> list = cri.list();
        session.close();
        if(list!=null&&list.size()>0)
        return list.get(0);
        return null;
    }

    @Override
    public void delete(Long id) {
        this.getHibernateTemplate().delete(findById(id));
    }
}
