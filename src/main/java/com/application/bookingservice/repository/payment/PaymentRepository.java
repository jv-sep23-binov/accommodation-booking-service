package com.application.bookingservice.repository.payment;

import com.application.bookingservice.model.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p LEFT JOIN FETCH p.booking b WHERE p.sessionId = :sessionId")
    Optional<Payment> findBySessionId(String sessionId);

    @Query("SELECT p FROM Payment p JOIN FETCH p.booking b "
            + "JOIN FETCH b.customer c "
            + "WHERE c.id = :customerId")
    List<Payment> findAllByCustomerId(Long customerId);
}
