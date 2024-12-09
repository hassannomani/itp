package com.nbr.trp.user.request;

import java.util.Set;

public class SignupRequest {
    private String username;

    private String email;

    private Set<String> roles;

    private String password;

    private String firstname;

    private String lastname;

    private String addedby;

    private String addedfromip;

    private String status;

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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddedby() {
        return addedby;
    }

    public String getAddedfromip() {
        return addedfromip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public void setAddedfromip(String addedfromip) {
        this.addedfromip = addedfromip;
    }
}
