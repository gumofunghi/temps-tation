package com.gumo.temps.repository;

import com.gumo.temps.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//for email confirmation
@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
//    @Query(value = "Select")
    ConfirmationToken findByConfirmationToken(String token);
}
