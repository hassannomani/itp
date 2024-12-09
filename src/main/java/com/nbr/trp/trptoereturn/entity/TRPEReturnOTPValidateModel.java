package com.nbr.trp.trptoereturn.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TRPEReturnOTPValidateModel {

    private String orgId;

    private String agentId;

    private String tinNo;

    private String phoneNo;

    private Integer otp;
}
