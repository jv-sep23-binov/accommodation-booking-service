package com.application.bookingservice.service.booking;

import com.application.bookingservice.dto.booking.BookingRequestDto;
import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.dto.booking.BookingUpdateRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    BookingResponseDto save(Long customerId, BookingRequestDto requestBookingDto);

    List<BookingResponseDto> getAll(Long customerId, Pageable pageable);

    BookingResponseDto findById(Long id);

    BookingResponseDto updateById(Long id, BookingUpdateRequestDto bookingUpdateRequestDto);

    void deleteById(Long id);

    List<BookingResponseDto> findByUserIdAndStatus(Long userId, String status, Pageable pageable);
}
