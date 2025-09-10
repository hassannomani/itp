package com.nbr.trp.common.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class smsinfo {
    public String sms_status;
    public String status_message;
    public String msisdn;
    public String sms_type;
    public String sms_body;
    public String csms_id;
    public String reference_id;
}
