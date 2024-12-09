package com.nbr.trp.common.repository;

import com.nbr.trp.common.entity.CityCorporation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface CityCorporationRepository extends JpaRepository<CityCorporation, String> {
    List<CityCorporation> findAll();
}
