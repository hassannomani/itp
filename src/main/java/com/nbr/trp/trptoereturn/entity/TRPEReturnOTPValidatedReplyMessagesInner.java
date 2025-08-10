package com.nbr.trp.trptoereturn.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TRPEReturnOTPValidatedReplyMessagesInner {

    private String id_token;

    private String refresh_token;

    private Boolean authentication;

    private Boolean success;

    private String redirectURL;

    private String responseType;


}
