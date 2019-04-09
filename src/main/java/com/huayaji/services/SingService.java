package com.huayaji.services;

import com.huayaji.entity.Product;
import com.huayaji.entity.Sing;

import java.util.List;

public interface SingService {

    public void save(Sing sing);
    public List<Sing> findAll();
    public Sing findById(Long id);
    public void update(Sing Sing);
    public void delete(Long id);

}
