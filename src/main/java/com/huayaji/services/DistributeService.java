package com.huayaji.services;

import com.huayaji.entity.Distribute;

import java.util.List;

public interface DistributeService {

    public void save(Distribute distribute);
    public List<Distribute> findAll();
    public Distribute findById(Long id);
    public void update(Distribute distribute);
    public void delete(Long id);

}
