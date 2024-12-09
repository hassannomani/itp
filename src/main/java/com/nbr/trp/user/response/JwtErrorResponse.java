package com.nbr.trp.user.response;

public class JwtErrorResponse {
    private String error;

    private int errorCode;


    public JwtErrorResponse(String error, int errorCode) {
        this.error = error;
        this.errorCode = errorCode;
    }

    public String getError() {
        return error;
    }

    public int getErrorCode() {
        return errorCode;
    }



    public void setError(String error) {
        this.error = error;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }


}
