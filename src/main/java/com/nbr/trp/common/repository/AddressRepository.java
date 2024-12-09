package com.nbr.trp.common.repository;

import com.nbr.trp.common.entity.Address;
import com.nbr.trp.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@EnableJpaRepositories
public interface AddressRepository extends JpaRepository<Address, String> {

    Address save(Address address);

    Address findByUuid(String uuid);
}
