package com.application.bookingservice.repository.booking;

import com.application.bookingservice.model.Booking;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAll(Specification<Booking> specification);

    @Query(value = "SELECT b FROM Booking b JOIN FETCH b.accommodation"
            + " JOIN FETCH b.customer WHERE b.customer.id = :customerID")
    List<Booking> findAllByCustomerId(Long customerID, Pageable pageable);
}
