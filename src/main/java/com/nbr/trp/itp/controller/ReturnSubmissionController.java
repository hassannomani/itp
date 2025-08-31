package com.nbr.trp.itp.controller;

import com.nbr.trp.common.service.CommonService;
import com.nbr.trp.itp.entity.ReturnSubmission;
import com.nbr.trp.itp.repository.ItpRepository;
import com.nbr.trp.itp.repository.ReturnSubmissionRepository;
import com.nbr.trp.itp.service.ReturnServiceImpl;
import com.nbr.trp.log.LoggerController;
import com.nbr.trp.user.repository.RoleRepository;
import com.nbr.trp.user.response.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/return")
public class ReturnSubmissionController {

    @Autowired
    ItpRepository itpRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ReturnSubmissionRepository returnSubmissionRepository;

    @Autowired
    CommonService commonService;

    @Autowired
    LoggerController loggerController;

    @Autowired
    ReturnServiceImpl returnService;

    @GetMapping("/check/{itp}/{taxpayer}")
    public ResponseEntity<?> checkIfValidated(HttpServletRequest request, @PathVariable String itp, @PathVariable String taxpayer){
        String ip = commonService.getIPAddress(request);
        try{
            ReturnSubmission ret = returnService.checkSubmission(itp,taxpayer);
            if(ret!=null)
                return ResponseEntity.ok(ret);
            else
                return ResponseEntity.ok(false);
            //loggerController.CertificateCheck(ip);
            //System.out.println("cert"+certificate);

        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/save/{itp}/{taxpayer}/{mobile}")
    public ResponseEntity<?> save(HttpServletRequest request, @PathVariable String itp, @PathVariable String taxpayer, @PathVariable String mobile){
        String ip = commonService.getIPAddress(request);
        try{
            ReturnSubmission ret = returnService.saveSubmission(itp,taxpayer, mobile);
            if(ret!=null)
                return ResponseEntity.ok(ret);
            else
                return ResponseEntity.ok(false);
            //loggerController.CertificateCheck(ip);

        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
