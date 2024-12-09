package com.nbr.trp.common.service;

import com.nbr.trp.common.entity.Address;
import com.nbr.trp.common.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImplemented implements AddressService{

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Address saveAddress(Address address) {
        Address ad = addressRepository.save(address);
        return ad;
    }

    @Override
    public Address getByUuid(String uuid) {
        Address ad = addressRepository.findByUuid(uuid);
        return ad;
    }

}
