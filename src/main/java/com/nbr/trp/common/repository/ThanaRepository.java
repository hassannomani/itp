package com.nbr.trp.common.repository;

import com.nbr.trp.common.entity.District;
import com.nbr.trp.common.entity.Thana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ThanaRepository extends JpaRepository<Thana, String> {

    List<Thana> findAll();
}
