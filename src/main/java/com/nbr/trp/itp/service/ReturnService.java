package com.nbr.trp.itp.service;

import com.nbr.trp.itp.entity.ITP;
import com.nbr.trp.itp.entity.ReturnSubmission;
import org.springframework.stereotype.Service;

@Service
public interface ReturnService {

    public ReturnSubmission saveSubmission(String itp, String taxpayer,String mobile);

    public ReturnSubmission checkSubmission(String itp, String taxpayer);
}
