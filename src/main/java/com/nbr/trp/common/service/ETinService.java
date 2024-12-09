package com.nbr.trp.common.service;

import com.nbr.trp.common.entity.ETinAuthModel;
import com.nbr.trp.common.entity.ETinResponseModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ETinService {

     ETinAuthModel getETinAuthModel();

     ETinResponseModel getTinResponse(String value);

     HttpEntity createHttpHeaders();
}
