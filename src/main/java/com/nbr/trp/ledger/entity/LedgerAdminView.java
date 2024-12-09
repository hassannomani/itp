package com.nbr.trp.ledger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

public interface LedgerAdminView {

    String getLid();

    String getTaxpayer_id();

    String getTaxpayer_name();

    String getPaid_amount();

    String getMinimum_tax();

    String getPayment_method();

     String getTransaction_id();

     String getChallan_id();

     String getAssessment_year();

     String getAgent_tin();

     String getRepresentative_tin();

     Double getAgent_commission();

     Double getRepresentative_commission();

     Date getCreated_at();

     String getBill_date();

     String getYear_no();

     String getRemarks();

     String getName();

     String getRe_name();

}
