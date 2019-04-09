package com.huayaji.dao;

import com.huayaji.entity.Sing;

import java.util.List;

public interface SingDao {

    public void save(Sing sing);
    public List<Sing> findAll();
    public Sing findById(Long id);
    public void update(Sing sing);
    public void delete(Long id);

}
