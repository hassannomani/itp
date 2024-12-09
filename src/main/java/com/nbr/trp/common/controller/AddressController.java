package com.nbr.trp.common.controller;


import com.nbr.trp.common.entity.Address;
import com.nbr.trp.common.repository.AddressRepository;
import com.nbr.trp.common.service.AddressService;
import com.nbr.trp.common.service.CommonService;
import com.nbr.trp.log.LoggerController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressService addressService;

    @Autowired
    CommonService commonService;

    @Autowired
    LoggerController loggerController;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/add")
    public ResponseEntity<?> addAddress(HttpServletRequest request,@RequestBody Address address) {
        String ip = commonService.getIPAddress(request);
        try{
            Address address1 = addressService.saveAddress(address);
            loggerController.InfoSave("Address",ip);
            return new ResponseEntity<>(address1, HttpStatus.CREATED);

        }catch(Exception e){
            loggerController.ErrorHandler(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}
