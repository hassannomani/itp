package com.nbr.trp.trptoereturn.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TRPEReturnAuthResponseModel {

    private TRPEReturnAuthReplyMessage replyMessage;

    private Boolean success;
}


