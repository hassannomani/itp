package com.nbr.trp.itp.repository;

import com.nbr.trp.itp.entity.AssessmentYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface YearRepository extends JpaRepository<AssessmentYear,String> {

    AssessmentYear save(String assessmentYear);

    AssessmentYear findByYearOrderByCreatedDesc(String year);

    @Query(value =
            "select top 1 * from assessment_year order by created desc",
            nativeQuery = true)
    AssessmentYear findLatestYear();

}
