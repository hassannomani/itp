package com.nbr.trp.common.entity;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EtinAddressModel {
    private String addr;
    private String addr1;
    private String city;
    private String distName;
    private String upzaName;
    private String thanaName;
    private String postCode;
    private String zipCode;

}
