package com.nbr.trp.user.response;

public class APILogInResponse {
    private String token;
    private String type = "Bearer";

    private Boolean success;

    public APILogInResponse(String token, String type, Boolean success) {
        this.token = token;
        this.type = type;
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
