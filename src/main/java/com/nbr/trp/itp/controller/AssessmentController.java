package com.nbr.trp.itp.controller;

import com.nbr.trp.itp.entity.AssessmentYear;
import com.nbr.trp.itp.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/year")
public class AssessmentController {

    @Autowired
    YearService yearService;

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add/{year}")
    public ResponseEntity<?> addYear(@PathVariable String year) {
        System.out.println(year);
        try{
            AssessmentYear assessmentYear = yearService.saveyear(year);
            // return ResponseEntity.ok(new MessageResponse("Representative registered successfully!"));
            return new ResponseEntity<>(assessmentYear, HttpStatus.CREATED);

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/find-latest")
    public ResponseEntity<?> findLastAssmntYear() {

        try{
            AssessmentYear assessmentYear = yearService.findLatest();
            // return ResponseEntity.ok(new MessageResponse("Representative registered successfully!"));
            return new ResponseEntity<>(assessmentYear, HttpStatus.CREATED);

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
