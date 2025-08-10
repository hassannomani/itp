package com.nbr.trp.trptoereturn.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nbr.trp.common.entity.ETinAuthModel;
import com.nbr.trp.common.entity.ETinAuthRequestModel;
import com.nbr.trp.common.entity.ETinResponseModel;
import com.nbr.trp.trptoereturn.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class TRPEReturnServiceImpl implements TRPEReturnService {

    @Value("${trpereturn.username}")
    private String userName;

    @Value("${trpereturn.password}")
    private String password;

    @Value("${trpereturn.systoken}")
    private String authTokenURL;

    @Value("${trpereturn.base-url}")
    private String baseURL;

    @Value("${trpereturn.usertoken}")
    private String ereturnURL;

    @Value("${trpereturn.live-base-url}")
    private String assmnt_live_base_url;

    @Value("${trpereturn.psr}")
    private String psr_url;

    @Autowired
    private Gson gson;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public TRPEReturnAuthResponseModel getAuthResponse() {
        TRPEReturnAuthRequestModel request = new TRPEReturnAuthRequestModel(userName, password, "password", true, "string");
        String url = baseURL + authTokenURL;
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        //System.out.println("url : {}"+ url);
        //System.out.println(request);
        HttpEntity<?> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<TRPEReturnAuthResponseModel> model = restTemplate.exchange(url, HttpMethod.POST, httpEntity, TRPEReturnAuthResponseModel.class);
        if (model != null) {
            TRPEReturnAuthResponseModel auth = model.getBody();
            System.out.println(auth.getReplyMessage().toString());
            return auth;

        } else {
            System.out.println("null");
            System.out.println(model);
            return null;
        }
    }

    @Override
    public HttpEntity createHttpHeaders() {
        TRPEReturnAuthResponseModel authModel = getAuthResponse();
        //System.out.println("printing----");
        System.out.println(authModel);
        TRPEReturnAuthReplyMessage msg = authModel.getReplyMessage();
        //System.out.println(msg);
        TRPEReturnAuthBody body = msg.getBody();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "bearer " + body.getId_token());
        return new HttpEntity<>("parameters", httpHeaders);

    }

    @Override
    public TRPEReturnPreOTPSendResponseModel getEReturnResponse(TRPEReturnOTPRequestModel request) {

        HttpEntity headers = createHttpHeaders();
        String url = baseURL + ereturnURL;
        System.out.println(headers.getHeaders());
        System.out.println(request);

        //System.out.println("url : " + url);
        HttpEntity<?> httpEntity = new HttpEntity<>(request, headers.getHeaders());
        ResponseEntity<TRPEReturnPreOTPSendResponseModel> eReturnResponse;

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // don't log nulls as empty
            String requestJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);

            System.out.println("=== Sending Request to API ===");
            System.out.println("URL: " + baseURL + ereturnURL);
            System.out.println("Headers: " + headers);
            System.out.println("Payload:\n" + requestJson);
            System.out.println("==============================");

        } catch (JsonProcessingException e) {
            System.err.println("Error serializing request object: " + e.getMessage());
        }

        try {
            eReturnResponse = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    httpEntity,
                    TRPEReturnPreOTPSendResponseModel.class
            );

            //eReturnResponse = restTemplate.exchange(url, HttpMethod.GET, httpHeadersEntity, String.class).getBody();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
        TRPEReturnPreOTPSendResponseModel finalResponseToReturn;
        finalResponseToReturn = eReturnResponse.getBody();
        //eReturnResponse = new Gson().fromJson(tinResponse, ETinResponseModel.class);
        if (finalResponseToReturn == null) {
            return null;
        }
        //System.out.println(eReturnResponse.toString());
        return finalResponseToReturn;
    }


    @Override
    public TRPEReturnOTPValidatedResponse validateOTP(TRPEReturnOTPValidateModel model) {

        HttpEntity headers = createHttpHeaders();
        String url = baseURL + ereturnURL;
        HttpEntity<?> httpEntity = new HttpEntity<>(model, headers.getHeaders());
        ResponseEntity<TRPEReturnOTPValidatedResponse> eReturnResponse;

        try {
            eReturnResponse = restTemplate.exchange(url, HttpMethod.POST, httpEntity, TRPEReturnOTPValidatedResponse.class);

            //eReturnResponse = restTemplate.exchange(url, HttpMethod.GET, httpHeadersEntity, String.class).getBody();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
        TRPEReturnOTPValidatedResponse finalResponseToReturn;
        finalResponseToReturn = eReturnResponse.getBody();
        //eReturnResponse = new Gson().fromJson(tinResponse, ETinResponseModel.class);
        if (finalResponseToReturn == null) {
            return null;
        }
        //System.out.println(eReturnResponse.toString());
        return finalResponseToReturn;
    }

//    @Override
//    public TRPAssessmentYearResponse checkPSR(String tin, String year){
//    return null;
//    }
 @Override
    public TRPAssessmentYearResponse checkPSR(String tin, String year){

        String url = assmnt_live_base_url + psr_url+tin+"/"+year;
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("url : {}"+ url);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        TRPAssessmentYearResponse model;
        int code = 0;
        String bodyAPI;
        try {
            bodyAPI = restTemplate.exchange(url,HttpMethod.POST, httpEntity, String.class).getBody();
            code = restTemplate.exchange(url,HttpMethod.POST, httpEntity, String.class).getStatusCode().value();
            //tinResponse = restTemplate.exchange(url, HttpMethod.GET, httpHeadersEntity, String.class).getBody();
        } catch (Exception ex) {
            System.out.println("Code isy "+code);

            System.out.println(ex.getMessage());
            //model = new Gson().fromJson(bodyAPI, TRPAssessmentYearResponse.class);
            TRPAssessmentYearResponse err = new TRPAssessmentYearResponse();
            err.setErrorCode("404");
            err.setSuccess(false);
            return err;
        }

        model = new Gson().fromJson(bodyAPI, TRPAssessmentYearResponse.class);

        System.out.println("Code is "+code);

        // System.out.println(model);

        if (model != null) {

            System.out.println("-----------------------");
//
//            System.out.println(model.getErrorCode());
//            System.out.println(model.getErrorMessage());
//            System.out.println(model.getSuccess());
            return model;

        }else{
            System.out.println("null");
            System.out.println(model);
            return null;
        }

    }

}
