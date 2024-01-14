package com.application.bookingservice.service.booking;

import static com.application.bookingservice.model.Booking.Status.PENDING;

import com.application.bookingservice.dto.booking.BookingRequestDto;
import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.dto.booking.BookingUpdateRequestDto;
import com.application.bookingservice.dto.booking.BookingUpdateStatusRequestDto;
import com.application.bookingservice.exception.EntityNotFoundException;
import com.application.bookingservice.exception.UnauthorizedActionException;
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
    private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Can't find customer by id: %d";
    private static final String BOOKING_NOT_FOUND_MESSAGE = "Can't find booking by id: %d";
    private static final String UNAUTHORIZED_FIND_MESSAGE =
            "Customers can't watch others booking "
            + "not related to them, customer id: %d, booking customer id: %d";
    private static final String UNAUTHORIZED_UPDATE_MESSAGE =
            "Customers can't update others booking "
            + "not related to them, customer id: %d, booking customer id: %d";
    private static final String UNAUTHORIZED_DELETE_MESSAGE =
            "Customers can't delete others booking"
            + " not related to them, customer id: %d, booking customer id: %d";
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingResponseDto save(Long customerId, BookingRequestDto requestBookingDto) {
        Booking booking = bookingMapper.toEntity(requestBookingDto);
        booking.setStatus(PENDING);
        booking.setCustomer(customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                String.format(CUSTOMER_NOT_FOUND_MESSAGE, customerId))
                ));
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Override
    public List<BookingResponseDto> getAll(Long customerId, Pageable pageable) {
        return bookingMapper.toDtos(
                bookingRepository.findAllByCustomerId(customerId, pageable)
        );
    }

    @Override
    public BookingResponseDto findById(Long customerId, Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                String.format(BOOKING_NOT_FOUND_MESSAGE, id))
                );
        Long bookingCustomerId = booking.getCustomer().getId();
        if (bookingCustomerId.equals(customerId)) {
            return bookingMapper.toDto(booking);
        }
        throw new UnauthorizedActionException(
                String.format(UNAUTHORIZED_FIND_MESSAGE, customerId, bookingCustomerId)
        );
    }

    @Override
    public BookingResponseDto updateById(
            Long customerId,
            Long id,
            BookingUpdateRequestDto requestDto) {
        Booking booking = bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(BOOKING_NOT_FOUND_MESSAGE, id))
        );
        Long bookingCustomerId = booking.getCustomer().getId();
        if (bookingCustomerId.equals(customerId)) {
            booking.setCheckIn(requestDto.getCheckIn());
            booking.setCheckOut(requestDto.getCheckOut());
            return bookingMapper.toDto(bookingRepository.save(booking));
        }
        throw new UnauthorizedActionException(
                String.format(UNAUTHORIZED_UPDATE_MESSAGE, customerId, bookingCustomerId)
        );
    }

    @Override
    public void deleteById(Long customerId, Long id) {
        Long bookingCustomerId = bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(BOOKING_NOT_FOUND_MESSAGE, id))
        ).getCustomer().getId();
        if (customerId.equals(bookingCustomerId)) {
            bookingRepository.deleteById(id);
        }
        throw new UnauthorizedActionException(
                String.format(UNAUTHORIZED_DELETE_MESSAGE, customerId, bookingCustomerId)
        );
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
                () -> new EntityNotFoundException(String.format(BOOKING_NOT_FOUND_MESSAGE, id))
        );
        booking.setStatus(requestDto.getStatus());
        return bookingMapper.toDto(bookingRepository.save(booking));
    }
}
