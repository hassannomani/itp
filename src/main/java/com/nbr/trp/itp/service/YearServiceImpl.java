package com.nbr.trp.itp.service;

import com.nbr.trp.itp.entity.AssessmentYear;
import com.nbr.trp.itp.repository.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YearServiceImpl implements  YearService{

    @Autowired
    YearRepository yearRepository;

    @Override
    public AssessmentYear saveyear(String year) {
        AssessmentYear yr= new AssessmentYear();
        yr.setYear(year);
        return yearRepository.save(yr);
    }

    @Override
    public AssessmentYear findYear(String year) {
        return yearRepository.findByYearOrderByCreatedDesc(year);
    }

    @Override
    public AssessmentYear findLatest() {
        return yearRepository.findLatestYear();
    }
}
