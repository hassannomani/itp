package com.nbr.trp.trptoereturn.entity;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TRPAssessmentYearReplyMessage {

    private String tinNo;

    private String assessmentYear;

    private String submissionStatus;

    private String assesName;

}
