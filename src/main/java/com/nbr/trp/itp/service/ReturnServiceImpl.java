package com.nbr.trp.itp.service;

import com.nbr.trp.itp.entity.ReturnSubmission;
import com.nbr.trp.itp.repository.ItpRepository;
import com.nbr.trp.itp.repository.ReturnSubmissionRepository;
import com.nbr.trp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReturnServiceImpl implements  ReturnService{

    @Autowired
    ItpRepository itpRepository;

    @Autowired
    ReturnSubmissionRepository returnSubmissionRepository;

    @Override
    public ReturnSubmission saveSubmission(String itp, String taxpayer, String mobile) {
        ReturnSubmission ret = new ReturnSubmission();
        ret.setItpTin(itp);
        ret.setTaxpayer(taxpayer);
        ret.setAssessmentYear("2025-2026");
        ret.setMobile(mobile);
        return returnSubmissionRepository.save((ret));
    }

    @Override
    public ReturnSubmission checkSubmission(String itp, String taxpayer) {
        return returnSubmissionRepository.findByItpTinAndTaxpayer(itp,taxpayer);
    }
}
