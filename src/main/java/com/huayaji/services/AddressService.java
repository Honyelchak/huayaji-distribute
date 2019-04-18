package com.huayaji.services;

import com.huayaji.entity.Address;

import java.util.List;

public interface AddressService {
    public void save(Address address);
    public List<Address> findAll();
    public Address findById(Long id);
    public void update(Address address);
    public void delete(Long id);
}
