package com.huayaji.services;

import com.huayaji.dao.SingDao;
import com.huayaji.entity.Sing;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SingServiceImpl implements SingService {

    @Resource
    private SingDao singDao;

    @Override
    public void save(Sing sing) {
        singDao.save(sing);
    }

    @Override
    public List<Sing> findAll() {
        return singDao.findAll();
    }

    @Override
    public Sing findById(Long id) {
        return singDao.findById(id);
    }

    @Override
    public void update(Sing sing) {
        singDao.update(sing);
    }

    @Override
    public void delete(Long id) {
        singDao.delete(id);
    }
}
