package com.application.bookingservice.service.booking;

import com.application.bookingservice.dto.booking.BookingRequestDto;
import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.dto.booking.BookingUpdateRequestDto;
import com.application.bookingservice.dto.booking.BookingUpdateStatusRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    BookingResponseDto save(Long customerId, BookingRequestDto requestBookingDto);

    List<BookingResponseDto> getAll(Long customerId, Pageable pageable);

    BookingResponseDto findById(Long customerId, Long id);

    BookingResponseDto updateById(
            Long customerId,
            Long id,
            BookingUpdateRequestDto bookingUpdateRequestDto);

    void deleteById(Long customerId, Long id);

    List<BookingResponseDto> findByCustomerIdAndStatus(Long userId,
                                                       String status,
                                                       Pageable pageable);

    BookingResponseDto updateStatus(Long id, BookingUpdateStatusRequestDto requestDto);
}
