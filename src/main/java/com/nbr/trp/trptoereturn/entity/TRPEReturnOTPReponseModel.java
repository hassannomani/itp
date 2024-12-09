package com.nbr.trp.trptoereturn.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TRPEReturnOTPReponseModel {

    private TRPEReturnOTPReponseReplyMessage replyMessage;

    private Boolean success;

    private String errorCode;

    private String errorMessage;

    private String message;
}
