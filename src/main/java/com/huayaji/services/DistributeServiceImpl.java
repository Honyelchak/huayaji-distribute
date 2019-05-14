package com.huayaji.services;

import com.huayaji.dao.DistributeDao;
import com.huayaji.entity.Distribute;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DistributeServiceImpl implements DistributeService{

    @Resource
    DistributeDao distributeDao;
    @Override
    public void save(Distribute distribute) {
        distributeDao.save(distribute);
    }

    @Override
    public List<Distribute> findAll() {
        return distributeDao.findAll();
    }

    @Override
    public Distribute findById(Long id) {
        return distributeDao.findById(id);
    }

    @Override
    public List<Distribute> findByUserid(String id) {
        return distributeDao.findByUserid(id);
    }

    @Override
    public void update(Distribute distribute) {
    distributeDao.update(distribute);
    }

    @Override
    public void delete(Long id) {
    distributeDao.delete(id);
    }


    @Override
    public void update(String days,String id, String distributeBalance, String distributeCountPer, String distributeTimeType,String distributeLastTime, String distributeTime, String comment) {
        distributeDao.update(days,id,distributeBalance,distributeCountPer,distributeTimeType,distributeLastTime,distributeTime,comment);
    }

    @Override
    public List<Distribute> findByPage(Integer page, Integer limit, String search) {
        return distributeDao.findByPage(page,limit,search);
    }

    @Override
    public long getCount(String search) {
        return distributeDao.getCount(search);
    }

    @Override
    public Distribute findByUseridAndProduct(String userid, String productid) {
        return distributeDao.findByUseridAndProduct(userid,productid);
    }
}
