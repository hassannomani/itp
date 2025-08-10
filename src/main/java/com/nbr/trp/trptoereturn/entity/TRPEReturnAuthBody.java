package com.nbr.trp.trptoereturn.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TRPEReturnAuthBody {

    private String id_token;

    private String refresh_token;

    private Boolean authentication;

    private Boolean success;

}
