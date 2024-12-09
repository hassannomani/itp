package com.nbr.trp.common.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ETinAuthModel {
    private boolean success;
    private String token;
}
