package com.application.bookingservice.service.booking;

import static com.application.bookingservice.model.Booking.Status.PENDING;

import com.application.bookingservice.dto.booking.BookingRequestDto;
import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.dto.booking.BookingUpdateRequestDto;
import com.application.bookingservice.dto.booking.BookingUpdateStatusRequestDto;
import com.application.bookingservice.exception.EntityNotFoundException;
import com.application.bookingservice.mapper.BookingMapper;
import com.application.bookingservice.model.Booking;
import com.application.bookingservice.repository.booking.BookingRepository;
import com.application.bookingservice.repository.customer.CustomerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingResponseDto save(Long customerId, BookingRequestDto requestBookingDto) {
        Booking booking = bookingMapper.toEntity(requestBookingDto);
        booking.setStatus(PENDING);
        booking.setCustomer(customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new EntityNotFoundException("can't find customer by id :"
                                + customerId)
                ));
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    public List<BookingResponseDto> getAll(Long customerId, Pageable pageable) {
        return bookingRepository.findAllByCustomerId(customerId, pageable).stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    public BookingResponseDto findById(Long id) {
        return bookingMapper.toDto(bookingRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find booking by id : " + id))
                );
    }

    @Override
    public BookingResponseDto updateById(Long id, BookingUpdateRequestDto bookingUpdateRequestDto) {
        Booking booking = bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find booking by id : " + id)
        );
        booking.setCheckIn(bookingUpdateRequestDto.getCheckIn());
        booking.setCheckOut(bookingUpdateRequestDto.getCheckOut());
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<BookingResponseDto> findByCustomerIdAndStatus(
            Long userId,
            String status,
            Pageable pageable) {
        Booking.Status bookingStatus = null;
        for (Booking.Status currentStatus : Booking.Status.values()) {
            if (currentStatus.name().equals(status)) {
                bookingStatus = currentStatus;
                break;
            }
        }
        if (bookingStatus == null) {
            throw new RuntimeException("not valid status : " + status);
        }
        return bookingRepository.findAllByCustomerIdAndStatus(
                userId,
                bookingStatus,
                pageable).stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    public BookingResponseDto updateStatus(Long id, BookingUpdateStatusRequestDto requestDto) {
        Booking booking = bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("can't find booking by id :" + id)
        );
        booking.setStatus(requestDto.getStatus());
        return bookingMapper.toDto(bookingRepository.save(booking));
    }
}
