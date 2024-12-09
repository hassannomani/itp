package com.nbr.trp.ledger.controller;

import com.nbr.trp.action.entity.Action;
import com.nbr.trp.common.service.CommonService;
import com.nbr.trp.ledger.entity.Metrics;
import com.nbr.trp.ledger.service.MetricsService;
import com.nbr.trp.log.LoggerController;
import com.nbr.trp.user.response.MessageResponse;
import com.nbr.trp.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {

    @Autowired
    MetricsService metricsService;

    @Autowired
    UserService userService;

    @Autowired
    CommonService commonService;

    @Autowired
    LoggerController loggerController;


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveMetrics(HttpServletRequest request,@RequestBody Metrics metrics) {
        String ip = commonService.getIPAddress(request);
        try{
            metricsService.saveMetrics(metrics);
            HashMap<String, String> map = new HashMap<>();
            loggerController.MetricsSave(ip,metrics.toString());
            map.put("response","ok");
            return ResponseEntity.ok(map);
        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllMetrics(HttpServletRequest request) {
        String ip = commonService.getIPAddress(request);
        try{
            List<Metrics> ls= metricsService.getAllMetrics();
            loggerController.ListGeneration("","All Metrics","Admin",ip);
            return ResponseEntity.ok(ls);
        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

}
