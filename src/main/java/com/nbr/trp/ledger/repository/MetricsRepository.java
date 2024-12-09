package com.nbr.trp.ledger.repository;

import com.nbr.trp.ledger.entity.Ledger;
import com.nbr.trp.ledger.entity.Metrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface MetricsRepository extends JpaRepository<Metrics, String> {

    List<Metrics> findByAssessmentYear(String id);

    Metrics save(Metrics metrics);

    Optional<Metrics> findByMid(String id);

    List<Metrics> findAll();

    List<Metrics> findByAssessmentYearAndYearNoOrderBySlotNo(String year,int yearno);

    List<Metrics> findAllByOrderBySlotNoAscYearNoAsc();


}
