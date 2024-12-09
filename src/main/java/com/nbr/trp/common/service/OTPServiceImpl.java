package com.nbr.trp.common.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.nbr.trp.common.entity.ETinResponseModel;
import com.nbr.trp.common.entity.OTP;
import com.nbr.trp.common.entity.OTPResponseModel;
import com.nbr.trp.common.repository.OTPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPServiceImpl implements OTPService{

    @Value("${otp.username}")
    private String userName;

    @Value("${otp.password}")
    private String password;

    @Value("${otp.base-url}")
    private String baseURL;

    @Value("${otp.otpsend-url}")
    private String sendURL;

    @Autowired
    private Gson gson;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OTPRepository otpRepository;

    private static final Integer EXPIRE_MINS = 5;
    private LoadingCache<String, Integer> otpCache;

    public OTPServiceImpl(){
        super();
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    @Override
    public OTPResponseModel sendOTP(String mobile) {
        System.out.println("here i come-------------");
        OTPResponseModel otpResponseModelret;
        String otp = generateOTP(mobile);
        String url = baseURL + sendURL+"txtMessage="+otp+"&msisdn="+mobile+
                "&usrname="+userName+"&password="+password;
        System.out.println("url : " + url);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        String otpResponse;
        try {
            //otpResponse = restTemplate.getForObject(url, String.class);
            otpResponse = restTemplate.exchange(url, HttpMethod.GET,httpEntity,String.class).getBody();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
        System.out.println("------");
        System.out.println(otpResponse);
        try{
            otpResponseModelret = new Gson().fromJson(otpResponse, OTPResponseModel.class);
            if (otpResponseModelret == null) {
                return null;
            }
            else{
                OTP otpObj = new OTP();
                otpObj.setOtp(String.valueOf(otp));
                otpObj.setMobile(mobile);
                otpRepository.save((otpObj));
                return otpResponseModelret;

            }
        }catch (Exception e){
            System.out.println("came here");

            System.out.println(e);
            return null;
        }
        //System.out.println(otpResponseModel);

    }

    public String generateOTP(String key){
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        otpCache.put(key, otp);
        return String.valueOf(otp);
    }

    public int getOtp(String key){
        try{
            return otpCache.get(key);
        }catch (Exception e){
            return 0;
        }
    }

    public void clearOTP(String key){
        otpCache.invalidate(key);
    }

    @Override
    public Boolean validateOTP(String mobile, String otp){
        String otpRetrieved = String.valueOf(getOtp(mobile));
        System.out.println(otpRetrieved);
        if(otpRetrieved.equals(otp))
            return true;
        else
            return false;
    }

    @Override
    public OTP saveOTP(OTP otp) {
        return otpRepository.save(otp);
    }

    @Override
    public OTP getOTP(String mobile) {
        return otpRepository.findByMobile(mobile);
    }
}
