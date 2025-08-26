package com.nbr.trp.itp.repository;

import com.nbr.trp.itp.entity.ITP;
import com.nbr.trp.itp.entity.ReturnSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ReturnSubmissionRepository extends JpaRepository<ReturnSubmission, String> {

    ReturnSubmission findByItpTinAndTaxpayerAndAssessmentYear(String tin, String taxpayer, String assessmentyear);
}
