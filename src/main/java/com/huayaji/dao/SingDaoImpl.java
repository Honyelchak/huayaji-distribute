package com.huayaji.dao;

import com.huayaji.entity.Sing;
import com.huayaji.entity.TemporarySing;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
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
    public List<TemporarySing> findDilivering() {
        TemporarySing valueBean = new TemporarySing();
        valueBean.setDistribute_status(1);
        return (List<TemporarySing>) this.getHibernateTemplate().findByValueBean("from TemporarySing as b where b.distribute_status =:distribute_status", valueBean);
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
    @Transactional
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
    @Transactional
    public List<TemporarySing> findTemporaryByPage(Integer page, Integer limit, String search) {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria cri = session.createCriteria(TemporarySing.class);
        if(search!=null&&search!=""&&!search.equals("")&&!search.equals(null))
            cri.add(Restrictions.like("user.id",Long.parseLong(search)));
        cri.setFirstResult((page-1)*limit);
        cri.setMaxResults(limit);
        List<TemporarySing> list = cri.list();
        session.close();
        if(list!=null&&list.size()>0)
            return list;
        return  null;
    }

    @Override
    @Transactional
    public List<Sing> findByPage(Integer page, Integer limit, String search) {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria cri = session.createCriteria(Sing.class);
        if(search!=null&&search!=""&&!search.equals("")&&!search.equals(null))
            cri.add(Restrictions.like("user.id",Long.parseLong(search)));
        cri.setFirstResult((page-1)*limit);
        cri.setMaxResults(limit);
        List<Sing> list = cri.list();
        session.close();
        if(list!=null&&list.size()>0)
            return list;
        return  null;
    }

    @Override
    @Transactional
    public void modifyStatus(String id) {
        Sing s=findById(Long.parseLong(id));
        s.setDistribute_status(2);
        update(s);
    }

    @Override
    @Transactional
    public List<Sing> findByUseridAndProductid(String phone, String s) {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria cri = session.createCriteria(Sing.class);
        cri.add(Restrictions.eq("user.id", Long.parseLong(phone)));
        cri.add(Restrictions.eq("product.id",Long.parseLong(s)));

        List<Sing> list = cri.list();
        session.close();
//        System.out.println("数据----"+list);
        if(list!=null&&list.size()>0){
//            System.out.println("数据----"+list);
            return list;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        this.getHibernateTemplate().delete(findById(id));
    }
    @Override
    @Transactional
    public long getCount(String search)
    {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria cri = session.createCriteria(Sing.class);
        if(search!=null&&search!=""&&!search.equals("")&&!search.equals(null))
            cri.add(Restrictions.like("user.id",Long.parseLong(search)));
        long count =(long)cri.setProjection(Projections.rowCount()).uniqueResult();
         cri = session.createCriteria(TemporarySing.class);
        if(search!=null&&search!=""&&!search.equals("")&&!search.equals(null))
            cri.add(Restrictions.like("user.id",Long.parseLong(search)));
         count +=(long)cri.setProjection(Projections.rowCount()).uniqueResult();
        return count;

    }
    @Transactional
    public int getCount()
    {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Criteria cri = session.createCriteria(Sing.class);

        int count =(int)cri.setProjection(Projections.rowCount()).uniqueResult();
        return count;

    }
}
