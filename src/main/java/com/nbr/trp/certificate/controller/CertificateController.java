package com.nbr.trp.certificate.controller;

import com.nbr.trp.certificate.entity.Certificate;
import com.nbr.trp.certificate.service.CertificateService;
import com.nbr.trp.common.service.CommonService;
import com.nbr.trp.ledger.entity.Ledger;
import com.nbr.trp.log.LoggerController;
import com.nbr.trp.user.response.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

    @Autowired
    CertificateService certificateService;

    @Autowired
    CommonService commonService;

    @Autowired
    LoggerController loggerController;

    @PostMapping("/bulk")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCertificates(HttpServletRequest request,@RequestBody List<Certificate> certificates){
        String ip = commonService.getIPAddress(request);

        try{
            certificateService.saveCertificateBulk(certificates);
            loggerController.CertificateBulk(ip);
            return ResponseEntity.ok(new MessageResponse("success"));

        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/duplicacy")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> checkDuplicateCertificates(HttpServletRequest request,@RequestBody List<String> tin){
        String ip = commonService.getIPAddress(request);

        try{
            List<Certificate> certificateList = certificateService.checkDuplicacy(tin);
            loggerController.CertificateDuplicacyCheck(ip);
            return ResponseEntity.ok(certificateList);

        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/check/{tin}/{nid}")
    public ResponseEntity<?> checkCertificate(HttpServletRequest request, @PathVariable String tin, @PathVariable String nid){
        String ip = commonService.getIPAddress(request);
        try{
            Boolean certificate = certificateService.getCertificateByTinNNid(tin, nid);
            loggerController.CertificateCheck(ip);
            System.out.println("cert"+certificate);
            return ResponseEntity.ok(certificate);

        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/get/{tin}/{nid}")
    public ResponseEntity<?> getCertificate(HttpServletRequest request, @PathVariable String tin, @PathVariable String nid){
        String ip = commonService.getIPAddress(request);

        try{
            Certificate certificate = certificateService.returnCertificateByTinNNid(tin, nid);
            loggerController.CertificateCheck(ip);

            //System.out.println("cert"+certificate);
            return ResponseEntity.ok(certificate);

        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCert(HttpServletRequest request){
        String ip = commonService.getIPAddress(request);
        try{
            List<Certificate> certs = certificateService.getAllCertificates();
            loggerController.ListGeneration("","All Certificates","Admin",ip);
            return ResponseEntity.ok(certs);

        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllCertPublic(HttpServletRequest request){
        String ip = commonService.getIPAddress(request);
        try{
            List<Certificate> certs = certificateService.getAllCertificates();
            loggerController.ListGeneration("","All Certificates Public","Admin",ip);
            return ResponseEntity.ok(certs);

        } catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
