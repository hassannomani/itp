package com.nbr.trp.trptoereturn.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TRPEReturnErrorHandlingResponse {

    private String replyMessage;

    private Boolean success;

    private String errorCode;

    private String errorMessage;

    private String message;
}
