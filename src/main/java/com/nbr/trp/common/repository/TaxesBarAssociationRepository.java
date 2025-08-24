package com.nbr.trp.common.repository;


import com.nbr.trp.common.entity.TaxesBarAssociation;
import com.nbr.trp.common.entity.Thana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface TaxesBarAssociationRepository extends JpaRepository<TaxesBarAssociation, String>
{
    List<TaxesBarAssociation> findAll();
}
