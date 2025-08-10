package com.nbr.trp.trptoereturn.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TRPEReturnOTPValidateModel {


    private String agentTin;

    private String taxpayerTin;

    private String taxpayerPhoneNo;

    private Integer otp;
}
