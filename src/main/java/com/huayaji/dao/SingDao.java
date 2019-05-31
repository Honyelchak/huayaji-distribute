package com.huayaji.dao;

import com.huayaji.entity.Sing;
import com.huayaji.entity.TemporarySing;

import java.util.List;

public interface SingDao {

    public void save(Sing sing);
    public List<Sing> findAll();
    public Sing findById(Long id);
    public void update(Sing sing);
    public void delete(Long id);
    public void saveTemporary(TemporarySing sing);
    public List<TemporarySing> findTemporaryAll();
    public TemporarySing findTemporaryById(Long id) ;
    public void updateTemporary(TemporarySing sing);
    public void deleteTemporary(Long id);
    public void deleteAllTem();
    public List<TemporarySing> findDilivering();
    public long getCount(String search);
    public int getCount();
    Sing findByUseridAndProductidAnddate(TemporarySing t);

    List<TemporarySing> findTemporaryByPage(Integer page, Integer limit, String search);

    List<Sing> findByPage(Integer page, Integer limit, String search);

    void modifyStatus(String id);

    List<Sing> findByUseridAndProductid(String phone, String s);
}
