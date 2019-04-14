package com.huayaji.dao;

import com.huayaji.entity.Address;

import java.util.List;

public interface AddressDao {
    public void save(Address address);

    public List<Address> findAll();

    public Address findById(Long id);

    public void update(Address address);

    public void delete(Long id);
}
