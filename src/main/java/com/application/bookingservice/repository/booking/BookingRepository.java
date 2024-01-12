package com.application.bookingservice.repository.booking;

import com.application.bookingservice.model.Booking;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAll(Specification<Booking> specification);
}
