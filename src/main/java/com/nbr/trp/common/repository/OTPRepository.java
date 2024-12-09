package com.nbr.trp.common.repository;

import com.nbr.trp.common.entity.OTP;
import com.nbr.trp.common.entity.Thana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface OTPRepository extends JpaRepository<OTP, String> {

    OTP save(OTP otp);

    OTP findByMobile(String mobile);
}
