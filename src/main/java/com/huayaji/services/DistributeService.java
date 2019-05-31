package com.huayaji.services;

import com.huayaji.entity.Distribute;

import java.util.List;

public interface DistributeService {

    public void save(Distribute distribute);
    public List<Distribute> findAll();
    public Distribute findById(Long id);
    public List<Distribute> findByUserid(String id);
    public void update(Distribute distribute);
    public void delete(Long id);
    void update(String days,String userid,String distributeCountPer);

    List<Distribute> findByPage(Integer page, Integer limit, String search);

    long getCount(String search);

    Distribute findByUseridAndProduct(String userid, String productid);
}
