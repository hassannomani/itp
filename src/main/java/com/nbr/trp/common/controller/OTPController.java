package com.nbr.trp.common.controller;

import com.nbr.trp.common.entity.OTPResponseModel;
import com.nbr.trp.common.service.CommonService;
import com.nbr.trp.common.service.OTPService;
import com.nbr.trp.log.LoggerController;
import com.nbr.trp.user.response.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "4200", maxAge = 4800)
@RestController
@RequestMapping("/api/otp")
public class OTPController {

    @Autowired
    OTPService otpService;

    @Autowired
    LoggerController loggerController;

    @Autowired
    CommonService commonService;

    @GetMapping("/{mobile}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> sendOTP(HttpServletRequest request, @PathVariable String mobile) {
        String ip = commonService.getIPAddress(request);
        try{
            OTPResponseModel otp = otpService.sendOTP(mobile);
            loggerController.IncomingRequest(ip,"/api/otp"+mobile);
            return ResponseEntity.ok(otp);
        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/validate/{mobile}/{otp}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> validateOTP(HttpServletRequest request,@PathVariable String mobile, @PathVariable String otp) {
        String ip = commonService.getIPAddress(request);
        try{
            Boolean otpMatch = otpService.validateOTP(mobile,otp);
            loggerController.IncomingRequest(ip,"/api/otp/validate"+mobile);

            return ResponseEntity.ok(otpMatch);
        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
