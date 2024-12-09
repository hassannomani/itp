package com.nbr.trp.common.repository;

import com.nbr.trp.common.entity.Address;
import com.nbr.trp.common.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface DistrictRepository extends JpaRepository<District, String> {

    List<District> findAll();
}
