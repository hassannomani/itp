package com.nbr.trp.itp.controller;

import com.nbr.trp.itp.repository.ItpRepository;
import com.nbr.trp.itp.repository.ReturnSubmissionRepository;
import com.nbr.trp.user.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
