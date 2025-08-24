package com.nbr.trp.common.repository;

import com.nbr.trp.common.entity.TaxesBarAssoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface TaxesBarAssocRepository extends JpaRepository<TaxesBarAssoc, String> {

    List<TaxesBarAssoc> findAll();
}
