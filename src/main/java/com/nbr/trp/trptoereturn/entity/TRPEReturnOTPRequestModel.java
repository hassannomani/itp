package com.nbr.trp.trptoereturn.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TRPEReturnOTPRequestModel {

    private String orgId;

    private String agentId;

    private String tinNo;

    private String phoneNo;
}
