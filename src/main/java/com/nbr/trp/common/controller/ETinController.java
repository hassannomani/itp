package com.nbr.trp.common.controller;

import com.nbr.trp.common.entity.ETinAuthRequestModel;
import com.nbr.trp.common.entity.ETinResponseModel;
import com.nbr.trp.common.service.CommonService;
import com.nbr.trp.common.service.ETinService;
import com.nbr.trp.common.service.EtinServiceImpl;
import com.nbr.trp.log.LoggerController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/v1/etin")
public class ETinController {

    @Autowired
    EtinServiceImpl eTinService;

    @Autowired
    CommonService commonService;

    @Autowired
    LoggerController loggerController;

    @Autowired
    public ETinController(EtinServiceImpl eTinService){
        this.eTinService = eTinService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/tin/{value}")
    public ETinResponseModel getTinResponse(HttpServletRequest request,@PathVariable String value) {
        String ip = commonService.getIPAddress(request);
        loggerController.IncomingRequest(ip,"/api/etin/tin"+value);
        ETinResponseModel response = eTinService.getTinResponse(value);
        return response;
    }
}