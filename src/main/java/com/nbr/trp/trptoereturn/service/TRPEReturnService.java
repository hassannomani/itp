package com.nbr.trp.trptoereturn.service;

import com.nbr.trp.common.entity.ETinAuthModel;
import com.nbr.trp.common.entity.ETinResponseModel;
import com.nbr.trp.trptoereturn.entity.*;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

@Service
public interface TRPEReturnService {

    TRPEReturnAuthResponseModel getAuthResponse();

    TRPEReturnOTPReponseModel getEReturnResponse(TRPEReturnOTPRequestModel value);

    HttpEntity createHttpHeaders();

    TRPEReturnOTPValidatedResponse validateOTP(TRPEReturnOTPValidateModel model);

    TRPAssessmentYearResponse checkPSR(String tin, String year);
}
