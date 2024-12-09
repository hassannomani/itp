package com.nbr.trp.common.service;

import com.nbr.trp.common.entity.Address;
import com.nbr.trp.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AddressService {

    Address saveAddress(Address address);

    Address getByUuid(String uuid);
}
