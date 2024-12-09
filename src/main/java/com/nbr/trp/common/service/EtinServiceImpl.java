package com.nbr.trp.common.service;

import com.google.gson.Gson;
import com.nbr.trp.common.entity.ETinAuthModel;
import com.nbr.trp.common.entity.ETinAuthRequestModel;
import com.nbr.trp.common.entity.ETinResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EtinServiceImpl implements  ETinService{

    @Value("${etin.username}")
    private String userName;

    @Value("${etin.password}")
    private String password;

    @Value("${etin.token-url}")
    private String authTokenURL;

    @Value("${etin.base-url}")
    private String baseURL;

    @Value("${etin.tin-url}")
    private String tinURL;

    @Autowired
    private Gson gson;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ETinAuthModel getETinAuthModel() {
        //restTemplate = new RestTemplate();
        ETinAuthRequestModel request = new ETinAuthRequestModel(userName, password);
        String url = baseURL + authTokenURL;
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("url : {}"+ url);
        System.out.println(request);
        HttpEntity<?> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<ETinAuthModel> model = restTemplate.exchange(url,HttpMethod.POST, httpEntity, ETinAuthModel.class);
        if (model != null) {
            ETinAuthModel auth = model.getBody();
            System.out.println(model.toString());
            return auth;

        }else{
            System.out.println("null");
            System.out.println(model);
            return null;
        }

    }

    @Override
    public ETinResponseModel getTinResponse(String value) {

        HttpEntity httpHeadersEntity = createHttpHeaders();
        ETinResponseModel response;
        String url = baseURL + tinURL+value;
        System.out.println("url : " + url);
        String tinResponse;
        try {
            tinResponse = restTemplate.exchange(url, HttpMethod.GET, httpHeadersEntity, String.class).getBody();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
        response = new Gson().fromJson(tinResponse, ETinResponseModel.class);
        if (response == null) {
            return null;
        }
        System.out.println(response.toString());

        return response;


    }

    @Override
    public HttpEntity createHttpHeaders() {
        ETinAuthModel authModel = getETinAuthModel();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "bearer " + authModel.getToken());
        return new HttpEntity<>("parameters", httpHeaders);

    }
}
