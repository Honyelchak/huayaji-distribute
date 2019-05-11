package com.huayaji.dao;


import com.huayaji.entity.Order;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public class OrderDaoImpl extends HibernateDaoSupport implements OrderDao{

    @Resource
    public void setMySessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public void update(String id, String totalMoney, String distributeTime, String distributeType, String count) {
        Order order = this.getHibernateTemplate().get(Order.class, Long.parseLong(id));//根据id查询信息
        DateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        try {
            order.setDistributeTime(new Timestamp( format.parse(distributeTime).getTime()));
            order.setCount(Integer.parseInt(count));
            order.setTotalMoney(Double.parseDouble(totalMoney));
            order.setDistributeType(Integer.parseInt(distributeType));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.getHibernateTemplate().update(order);
    }

    @Override
    public void save(Order user) {
        this.getHibernateTemplate().save(user);
    }

    @Override
    public List<Order> findAll() {
        return this.getHibernateTemplate().loadAll(Order.class);
    }

    @Override
    public Order findById(Long id) {
        return this.getHibernateTemplate().get(Order.class, id);
    }
    @Override
   public Order findByUseridAndProductid(String  userid,String productid) {
       DetachedCriteria criteria = DetachedCriteria.forClass(Order.class);
       criteria.add(Restrictions.eq("userid", userid));
       criteria.add(Restrictions.eq("product", productid));
       List<Order> list = (List<Order>) getHibernateTemplate().findByCriteria(criteria);
       if(list!=null&&list.size()>0)
       return list.get(0);
       return null;
   }
    @Override
    public void update(Order user) {
        this.getHibernateTemplate().update(user);
    }

    @Override
    public void delete(Long id) {
        this.getHibernateTemplate().delete(findById(id));
    }

}
