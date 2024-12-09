package com.nbr.trp.common.service;

import com.nbr.trp.common.entity.OTP;
import com.nbr.trp.common.entity.OTPResponseModel;
import org.springframework.stereotype.Service;

@Service
public interface OTPService {
    OTPResponseModel sendOTP(String mobile);

    Boolean validateOTP(String mobile, String otp);

    OTP saveOTP(OTP otp);

    OTP getOTP(String mobile);
}
