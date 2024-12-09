package com.nbr.trp.itp.service;

import com.nbr.trp.itp.entity.AssessmentYear;
import org.springframework.stereotype.Service;

@Service
public interface YearService {

    AssessmentYear saveyear (String year);

    AssessmentYear findYear(String year);
    AssessmentYear findLatest();


}
