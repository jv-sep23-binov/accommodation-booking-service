package com.application.bookingservice.service.booking;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    @Override
    public Object save(Long customerId, Object requestBookingDto) {
        return null;
    }

    @Override
    public List<Object> getAll(Long customerId, Pageable pageable) {
        return null;
    }

    @Override
    public Object findById(Long id) {
        return null;
    }

    @Override
    public Object updateById(Long id, Object bookingRequestDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Object> findByUserIdAndStatus(Long userId, Object status, Pageable pageable) {
        return null;
    }
}
