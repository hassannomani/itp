package com.nbr.trp.user.filter;

import com.google.gson.Gson;
import com.nbr.trp.common.entity.OTPResponseModel;
import com.nbr.trp.common.service.CommonService;
import com.nbr.trp.log.LoggerController;
import com.nbr.trp.user.entity.ErrorStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.io.IOException;
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Autowired
    CommonService commonService;

    @Autowired
    LoggerController loggerController;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        logger.error("Unauthorized error: {}", authException.getMessage());

        String ip = commonService.getIPAddress(request);
        loggerController.UnAuthorized("Unauthorized access from "+ip);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ErrorStatus err = new ErrorStatus(401,"Token Error");
        String json = new Gson().toJson(err, ErrorStatus.class);
        response.setStatus(401);
        response.getWriter().write(json);
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");

    }

    private void setUnauthorizedResponse(HttpServletResponse response, String reason) {
        try {
            ErrorStatus err = new ErrorStatus(401,reason);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            String json = new Gson().toJson(err, OTPResponseModel.class);
            response.setStatus(401);
            response.getWriter().write(json);
        } catch (IOException e) {
            logger.error(String.format(e.getMessage()));
        }
    }


}
