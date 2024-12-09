package com.nbr.trp.ledger.entity;

public class LedgeAPIResponse {

    private String message = "";

    private Boolean success;

    private String redirectUrl;

    public LedgeAPIResponse(String message, Boolean success, String redirectUrl) {
        this.message = message;
        this.success = success;
        this.redirectUrl=redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
