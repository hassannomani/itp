package com.nbr.trp.trptoereturn.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TRPAssessmentYearResponse {

    private TRPAssessmentYearReplyMessage replyMessage;

    private Boolean success;

    private String errorCode;

    private String errorMessage;

}
