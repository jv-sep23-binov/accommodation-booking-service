package com.application.bookingservice.service.booking;

import static com.application.bookingservice.model.Booking.Status.EXPIRED;
import static com.application.bookingservice.model.Booking.Status.PENDING;

import com.application.bookingservice.dto.booking.BookingRequestDto;
import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.dto.booking.BookingSearchParametersDto;
import com.application.bookingservice.dto.booking.BookingUpdateRequestDto;
import com.application.bookingservice.dto.booking.BookingUpdateStatusRequestDto;
import com.application.bookingservice.exception.EntityNotFoundException;
import com.application.bookingservice.exception.UnauthorizedActionException;
import com.application.bookingservice.mapper.BookingMapper;
import com.application.bookingservice.model.Booking;
import com.application.bookingservice.repository.booking.BookingRepository;
import com.application.bookingservice.repository.booking.spec.BookingSpecificationBuilder;
import com.application.bookingservice.repository.customer.CustomerRepository;
import com.application.bookingservice.service.bot.NotificationService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private static final String EXPIRED_MESSAGE = "Booking with id: %d was Expired";
    private static final String NOTHING_EXPIRED_MESSAGE = "No expired bookings today!";
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
    private static final String UNAUTHORIZED_BOOKING_MESSAGE =
            "Can't book already booked accommodation booked check_in: %tF "
            + "check_out: %tF booking check_in: %tF check_out: %tF accommodationID: %d";
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final BookingSpecificationBuilder specificationBuilder;
    private final BookingMapper bookingMapper;
    private final NotificationService notificationService;

    @Override
    public BookingResponseDto save(Long customerId, BookingRequestDto requestBookingDto) {
        Booking bookingToSave = bookingMapper.toEntity(requestBookingDto);
        Long accommodationId = requestBookingDto.getAccommodationId();
        List<Booking> byAccommodationId = bookingRepository
                .findByAccommodationId(accommodationId);
        LocalDate requestedCheckIn = requestBookingDto.getCheckIn();
        LocalDate requestedCheckOut = requestBookingDto.getCheckOut();
        for (Booking booking : byAccommodationId) {
            LocalDate bookedCheckIn = booking.getCheckIn();
            LocalDate bookedCheckOut = booking.getCheckOut();
            if ((requestedCheckIn.isAfter(bookedCheckIn)
                    && requestedCheckIn.isBefore(bookedCheckOut))
                    || (requestedCheckOut.isAfter(bookedCheckIn)
                    && requestedCheckOut.isBefore(bookedCheckOut)
                    || requestedCheckOut.isEqual(bookedCheckOut)
                    || requestedCheckIn.isEqual(bookedCheckIn))
            ) {
                throw new UnauthorizedActionException(
                        String.format(UNAUTHORIZED_BOOKING_MESSAGE, bookedCheckIn,
                                bookedCheckOut, requestedCheckIn,
                                requestedCheckOut, accommodationId));
            }
        }
        bookingToSave.setStatus(PENDING);
        bookingToSave.setCustomer(customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                String.format(CUSTOMER_NOT_FOUND_MESSAGE, customerId))
                ));
        BookingResponseDto savedBookingDto = bookingMapper
                .toDto(bookingRepository.save(bookingToSave));
        notificationService.bookingsCreatedMessage(savedBookingDto);
        return savedBookingDto;
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
            notificationService.bookingCanceledMessage(id);
        } else {
            throw new UnauthorizedActionException(
                    String.format(UNAUTHORIZED_DELETE_MESSAGE, customerId, bookingCustomerId)
            );
        }
    }

    @Override
    public List<BookingResponseDto> search(
            BookingSearchParametersDto searchParameters
    ) {
        Specification<Booking> specification = specificationBuilder.build(searchParameters);
        return bookingRepository.findAll(specification).stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    public BookingResponseDto updateStatus(Long id, BookingUpdateStatusRequestDto requestDto) {
        Booking booking = bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(BOOKING_NOT_FOUND_MESSAGE, id))
        );
        booking.setStatus(requestDto.getStatus());
        BookingResponseDto updatedBookingDto = bookingMapper.toDto(bookingRepository.save(booking));
        notificationService.bookingStatusChangedMessage(updatedBookingDto);
        return updatedBookingDto;
    }

    @Scheduled(cron = "@daily")
    private void checkBookingDate() {
        boolean expired = false;
        LocalDate nowDate = LocalDate.now();
        List<Booking> bookings = bookingRepository.findAll();
        for (Booking booking : bookings) {
            if (booking.getCheckOut().isAfter(nowDate)
                    || booking.getCheckOut().isEqual(nowDate)) {
                booking.setStatus(EXPIRED);
                bookingRepository.save(booking);
                expired = true;
                notificationService.bookingExpiredMessage(
                        String.format(EXPIRED_MESSAGE, booking.getId())
                );
            }
        }
        if (!expired) {
            notificationService.bookingExpiredMessage(NOTHING_EXPIRED_MESSAGE);
        }
    }
}
