package com.nbr.trp.user.response;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";

    private String uuid;
    private String username;
    private String email;
    private List<String> roles;

    private String name;

    public JwtResponse(String accessToken, String uuid, String username, String email, List<String> roles, String name) {
        this.token = accessToken;
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return uuid;
    }

    public void setId(String id) {
        this.uuid = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



}
