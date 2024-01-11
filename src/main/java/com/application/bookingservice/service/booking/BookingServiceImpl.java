package com.application.bookingservice.service.booking;

import java.util.List;

import com.application.bookingservice.dto.booking.BookingRequestDto;
import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.dto.booking.BookingUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    @Override
    public BookingResponseDto save(Long customerId, BookingRequestDto requestBookingDto) {
        return null;
    }

    @Override
    public List<BookingResponseDto> getAll(Long customerId, Pageable pageable) {
        return null;
    }

    @Override
    public BookingResponseDto findById(Long id) {
        return null;
    }

    @Override
    public BookingResponseDto updateById(Long id, BookingUpdateRequestDto bookingUpdateRequestDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<BookingResponseDto> findByUserIdAndStatus(Long userId, Object status, Pageable pageable) {
        return null;
    }
}
