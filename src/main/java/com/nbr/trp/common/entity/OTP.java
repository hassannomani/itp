package com.nbr.trp.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@IdClass(OTPKeys.class)
@Setter
@Getter
@NoArgsConstructor
@Table(name = "otp")
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "otpid", columnDefinition = "uniqueidentifier default newid()")
    public String otpid;

    @Id
    @Column(name = "otp", nullable = false)
    public String otp;

    @Id
    @Column(name = "mobile", nullable = false)
    public String mobile;

    @Column(name = "creation_date", updatable = false)
    @CreationTimestamp
    public LocalDateTime addedDate;
}
