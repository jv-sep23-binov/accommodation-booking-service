package com.application.bookingservice.service.booking;

import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    Object save(Long customerId, Object requestBookingDto);

    List<Object> getAll(Long customerId, Pageable pageable);

    Object findById(Long id);

    Object updateById(Long id, Object bookingRequestDto);

    void deleteById(Long id);

    List<Object> findByUserIdAndStatus(Long userId, Object status, Pageable pageable);
}
