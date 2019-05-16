package com.huayaji.services;

import com.huayaji.entity.Product;
import com.huayaji.entity.Sing;
import com.huayaji.entity.TemporarySing;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.util.Collection;
import java.util.List;

public interface SingService {

    public void save(Sing sing);
    public List<Sing> findAll();
    public Sing findById(Long id);
    public void update(Sing Sing);
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
}
