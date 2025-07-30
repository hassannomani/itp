package com.nbr.trp.common.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OTPResponseModel {

//    private String is_success;
//    private String trn_id;
//    private String error_code;
//    private String message;
//    private String time;
//    private String cell_no;

    private String  status;
    private String message;
    private String smsid;
    private String SmsCount;
    private String errmsg;

}
