package com.nbr.trp.itp.controller;

import com.nbr.trp.common.service.CommonService;
import com.nbr.trp.itp.entity.ITP;
import com.nbr.trp.log.LoggerController;
import com.nbr.trp.itp.entity.RepresentativeAgentView;
import com.nbr.trp.itp.repository.RepresentativeRepository;
import com.nbr.trp.itp.service.RepresentativeService;
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
    RepresentativeRepository representativeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RepresentativeService representativeService;

    @Autowired
    CommonService commonService;

    @Autowired
    LoggerController loggerController;

    @PostMapping("/add")
    public ResponseEntity<?> addRepresentative(@RequestBody ITP ITP) {
        if (representativeRepository.existsByTinNo(ITP.getTinNo())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: username is already taken!"));
        }

        try{
            ITP ITP1 = representativeService.saveRepresentative(ITP);
               // return ResponseEntity.ok(new MessageResponse("Representative registered successfully!"));
            return new ResponseEntity<>(ITP1, HttpStatus.CREATED);

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        List<ITP> ls = representativeService.getAllRepresentatives();
        loggerController.ListGeneration("","All Representatives", "Admin",commonService.getIPAddress(request));
        return ResponseEntity.ok(ls);

    }

    @GetMapping("/{tin}")
    public ResponseEntity<?> getARepresentative(HttpServletRequest request, @PathVariable String tin){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        loggerController.TRPIndividualRetrival(userDetails.getUsername(),tin,ip);
        Optional<ITP> representative = representativeService.getUserByTin(tin);
        return ResponseEntity.ok(representative);

    }

    @GetMapping("/agent/{tin}")
    public ResponseEntity<?> getAllRepresentativeOfAnAgent(HttpServletRequest request,@PathVariable String tin){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        List<ITP> ITPList = representativeService.getAllRepresentativesOfAnAgent(tin);
        loggerController.ListGeneration(userDetails.getUsername(),"All TRP of Agent: "+tin,"",ip);
        return ResponseEntity.ok(ITPList);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/assign/{tin}/{agent}")
    public ResponseEntity<?> assignAgent(HttpServletRequest request,@PathVariable String tin, @PathVariable String agent){
        String ip = commonService.getIPAddress(request);
        try{
            loggerController.TRPAssign(tin,agent,ip);
            ITP ITP1 = representativeService.assignAgent(tin, agent);
            return new ResponseEntity<>(ITP1, HttpStatus.OK);

        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PreAuthorize("hasRole('REPRESENTATIVE')")
    @GetMapping("/agentinfo/{tin}")
    public ResponseEntity<?> getAgentDetails(HttpServletRequest request, @PathVariable String tin){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        loggerController.TRPIndividualRetrival(userDetails.getUsername(),tin,ip);
        RepresentativeAgentView representative = representativeService.getAgentInfo(tin);
        return ResponseEntity.ok(representative);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/single/{agent}/{trp}")
    public ResponseEntity<?> getSingleTRPOfAnAgent(HttpServletRequest request,@PathVariable String agent, @PathVariable String trp){
        String ip = commonService.getIPAddress(request);
        UserDetailsImpl userDetails = commonService.getDetails();
        ITP ITP = representativeService.getSingleRepresentativesOfAnAgent(agent, trp);
        //loggerController.ListGeneration(userDetails.getUsername(),"All TRP of Agent: "+tin,"",ip);
        return ResponseEntity.ok(ITP);
    }


}
