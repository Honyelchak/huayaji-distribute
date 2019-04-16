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
    public void update(Distribute distribute) {
    distributeDao.update(distribute);
    }

    @Override
    public void delete(Long id) {
    distributeDao.delete(id);
    }
}