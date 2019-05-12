package com.huayaji.services;

import com.huayaji.dao.SingDao;
import com.huayaji.entity.Sing;
import com.huayaji.entity.TemporarySing;
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

    @Override
    public void saveTemporary(TemporarySing sing) {
        singDao.saveTemporary(sing);
    }

    @Override
    public List<TemporarySing> findTemporaryAll() {
        return singDao.findTemporaryAll();
    }

    @Override
    public TemporarySing findTemporaryById(Long id) {
        return singDao.findTemporaryById(id);
    }

    @Override
    public void updateTemporary(TemporarySing sing) {
    singDao.updateTemporary(sing);
    }

    @Override
    public void deleteTemporary(Long id) {
        singDao.deleteTemporary(id);
    }

    @Override
    public void deleteAllTem() {
        singDao.deleteAllTem();
    }

    @Override
    public List<TemporarySing> findDilivering() {return singDao.findDilivering();}

}
