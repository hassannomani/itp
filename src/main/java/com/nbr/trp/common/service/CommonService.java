package com.nbr.trp.common.service;

import com.nbr.trp.common.entity.*;
import com.nbr.trp.user.service.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommonService {
    List<Division> getAllDivision();

    List<District> getAllDistrict();

    List<Thana> getAllThana();

    List<CityCorporation> getAllCityCorporation();

    List<TaxesBarAssociation> getAllTaxesBar();

    UserDetailsImpl getDetails();

    String getIPAddress(HttpServletRequest request);


}
