package com.nbr.trp.itp.controller;

import com.nbr.trp.common.service.CommonService;
import com.nbr.trp.itp.entity.ITP;
import com.nbr.trp.log.LoggerController;
import com.nbr.trp.itp.entity.RepresentativeAgentView;
import com.nbr.trp.itp.repository.ItpRepository;
import com.nbr.trp.itp.service.ItpService;
import com.nbr.trp.user.repository.RoleRepository;
import com.nbr.trp.user.response.MessageResponse;
import com.nbr.trp.user.service.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/representative")
public class ItpController {

    @Autowired
    ItpRepository itpRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ItpService itpService;

    @Autowired
    CommonService commonService;

    @Autowired
    LoggerController loggerController;

    @PostMapping("/add")
    public ResponseEntity<?> addITP(@RequestBody ITP ITP) {
        if (itpRepository.existsByTinNo(ITP.getTinNo())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: username is already taken!"));
        }

        try{
            ITP ITP1 = itpService.saveRepresentative(ITP);
               // return ResponseEntity.ok(new MessageResponse("Representative registered successfully!"));
            return new ResponseEntity<>(ITP1, HttpStatus.CREATED);

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        List<ITP> ls = itpService.getAllITPs();
        loggerController.ListGeneration("","All ITPs", "Admin",commonService.getIPAddress(request));
        return ResponseEntity.ok(ls);

    }

    @GetMapping("/{tin}")
    public ResponseEntity<?> getAnITP(HttpServletRequest request, @PathVariable String tin){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        loggerController.TRPIndividualRetrival(userDetails.getUsername(),tin,ip);
        Optional<ITP> representative = itpService.getUserByTin(tin);
        return ResponseEntity.ok(representative);

    }




}
