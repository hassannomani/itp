package com.nbr.trp.trptoereturn.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TRPEReturnOTPRequestModel {

    //private String orgId;

    private String agentTin;

    private String taxpayerTin;

    private String taxpayerPhoneNo;

}
