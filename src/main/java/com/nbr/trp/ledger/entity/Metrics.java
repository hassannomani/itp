package com.nbr.trp.ledger.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "metrics")
public class Metrics {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uniqueidentifier default newid()")
    public String mid;

    @Column(name = "assessment_year",nullable = false)
    public String assessmentYear;

    @Column(name = "payment_rule")
    public Double paymentRule;

    @Column(name = "tax_amount",nullable = false)
    public Double taxAmount;

    @Column(name = "agent_rate",nullable = false)
    public Double agentRate;

    @Column(name = "representative_rate",nullable = false)
    public Double representativeRate;

    @Column(name = "year_no",nullable = false)
    public int yearNo;

    @Column(name = "slot_no",nullable = false)
    public int slotNo;

    @Column(name = "created_at")
    @CreationTimestamp
    public Timestamp created_at;

    public Metrics(String mid, String assessmentYear, Double paymentRule, Double taxAmount, Double agentRate, Double representativeRate, int yearNo, int slotNo, Timestamp created_at) {
        this.mid = mid;
        this.assessmentYear = assessmentYear;
        this.paymentRule = paymentRule;
        this.taxAmount = taxAmount;
        this.agentRate = agentRate;
        this.representativeRate = representativeRate;
        this.yearNo = yearNo;
        this.slotNo = slotNo;
        this.created_at = created_at;
    }
}
