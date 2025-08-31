package com.nbr.trp.trptoereturn.controller;

import com.google.gson.Gson;
import com.nbr.trp.common.entity.ETinResponseModel;
import com.nbr.trp.common.service.CommonService;
import com.nbr.trp.common.service.EtinServiceImpl;
import com.nbr.trp.log.LoggerController;
import com.nbr.trp.trptoereturn.entity.*;
import com.nbr.trp.trptoereturn.service.TRPEReturnService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/trpereturn")
public class TRPEReturnController {

    @Autowired
    TRPEReturnService trpeReturnService;

    @Autowired
    CommonService commonService;

    @Autowired
    LoggerController loggerController;

    @PostMapping("")
    public ResponseEntity<TRPEReturnPreOTPSendResponseModel> getOTPMsg(HttpServletRequest request, @RequestBody TRPEReturnOTPRequestModel val) {

//        String ip = commonService.getIPAddress(request);
//        try {
//            TRPEReturnOTPReponseModel response = trpeReturnService.getEReturnResponse(val);
//            loggerController.OTPRequest(ip);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }catch(Exception e){
//            TRPEReturnOTPReponseModel model = new TRPEReturnOTPReponseModel();
//            model.setErrorMessage(e.getMessage());
//            model.setSuccess(false);
//            model.setErrorCode("500");
//            //System.out.println(e.g);
//            loggerController.ErrorHandler(e);
//            return new ResponseEntity<>(model, HttpStatus.OK);
        TRPEReturnPreOTPSendResponseModel response = trpeReturnService.getEReturnResponse(val);
        return new ResponseEntity<>(response, HttpStatus.OK);

//            return ResponseEntity
//                    .status(HttpStatus.CONFLICT)
//                    .body(model);



    }

    @PostMapping("/otp")
    public ResponseEntity<?> ValidateOTP(HttpServletRequest request,@RequestBody TRPEReturnOTPValidateModel val) {
        try{
            TRPEReturnOTPValidatedResponse response = trpeReturnService.validateOTP(val);
            loggerController.OTPValidate(commonService.getIPAddress(request));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
//            Map<String, String> exc_map = new HashMap<String, String>();
//            exc_map.put("message", e.toString());
//            exc_map.put("stacktrace", getStackTrace(e));
            loggerController.ErrorHandler(e);
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());

        }

    }

    @GetMapping("/psr/{year}/{tin}")
    public ResponseEntity<?> checkPSROfTRP(HttpServletRequest request,@PathVariable String year, @PathVariable String tin) {
        try{
            TRPAssessmentYearResponse response = trpeReturnService.checkPSR(tin,year);
            //loggerController.OTPValidate(commonService.getIPAddress(request));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
//            Map<String, String> exc_map = new HashMap<String, String>();
//            exc_map.put("message", e.toString());
//            exc_map.put("stacktrace", getStackTrace(e));
            System.out.println(e);
            loggerController.ErrorHandler(e);
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());

        }

    }

    @PostMapping("/validated")
    public ResponseEntity<?> ereturnValidatedGateway(HttpServletRequest request, @RequestBody TRPEReturnOTPRequestModel val) {
        try {
            TRPEReturnOTPValidatedResponse response = trpeReturnService.getEReturnResponseForAlreadyValidated(val);
            loggerController.OTPValidate(commonService.getIPAddress(request));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {

            loggerController.ErrorHandler(e);
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());

        }

    }
//    public static String getStackTrace(final Throwable throwable) {
//        final StringWriter sw = new StringWriter();
//        final PrintWriter pw = new PrintWriter(sw, true);
//        throwable.printStackTrace(pw);
//        return sw.getBuffer().toString();
//    }
}
