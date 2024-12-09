package com.nbr.trp.common.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ETinResponseModel {
    private String tin;
    private String name;
    private String assesName;
    private String nid;
    private String passportNumber;
    private String smartId;
    private String fathersName;
    private String mothersName;
    private String mobile;
    private String email;
    private String dob;
    private String nationality;
    private ETinZoneModel zone;
    private ETinCircleModel circle;
    private String status;
    private ETinAddress address;
    private Integer isError;
    private String errorDescription;

}
