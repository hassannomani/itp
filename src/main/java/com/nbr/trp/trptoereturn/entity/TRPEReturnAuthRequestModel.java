package com.nbr.trp.trptoereturn.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TRPEReturnAuthRequestModel {

    private String username;

    private String password;

    private String grantType;

    private Boolean rememberMe;

    private String refreshToken;
}
