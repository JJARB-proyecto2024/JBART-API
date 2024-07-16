package com.project.demo.logic.entity.Otp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByOtpCodeAndEmail(String email, String otpCode);

    @Query("SELECT o FROM Otp o WHERE o.expiryTime < :now")
    List<Otp> findExpiredOtps(LocalDateTime now);
}
