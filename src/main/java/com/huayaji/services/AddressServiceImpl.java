package com.huayaji.services;

import com.huayaji.dao.AddressDao;
import com.huayaji.entity.Address;

import javax.annotation.Resource;
import java.util.List;

public class AddressServiceImpl implements AddressService{
    @Resource
    AddressDao addressDao;
    @Override
    public void save(Address address) {
        addressDao.save(address);
    }

    @Override
    public List<Address> findAll() {
        return addressDao.findAll();
    }

    @Override
    public Address findById(Long id) {
        return addressDao.findById(id);
    }

    @Override
    public void update(Address address) {
        addressDao.update(address);
    }

    @Override
    public void delete(Long id) {
        addressDao.delete(id);
    }
}
