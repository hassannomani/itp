package com.nbr.trp.common.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ETinAuthRequestModel {
    private String UserName;
    private String Password;
}
