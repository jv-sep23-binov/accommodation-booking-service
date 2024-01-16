package com.application.bookingservice.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.mapper.BookingMapper;
import com.application.bookingservice.model.Accommodation;
import com.application.bookingservice.model.Address;
import com.application.bookingservice.model.Booking;
import com.application.bookingservice.model.Customer;
import com.application.bookingservice.repository.booking.BookingRepository;
import com.application.bookingservice.service.booking.BookingServiceImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    private static final Long ADDRESS_ID = 1L;
    private static final String ADDRESS_CITY = "Ivano-Frankivsk";
    private static final String ADDRESS_COUNTRY = "Ukraine";
    private static final String ADDRESS_STREET = "saint dog patron street";
    private static final Integer ADDRESS_BUILDING = 22;
    private static final Integer ADDRESS_FLAT = 2;
    private static final Long CUSTOMER_ID = 1L;
    private static final String CUSTOMER_EMAIL = "admin@example.com";
    private static final String CUSTOMER_PASSWORD = "123";
    private static final String CUSTOMER_LAST_NAME = "Doe";
    private static final String CUSTOMER_FIRST_NAME = "Admin";
    private static final String ACCOMMODATION_SIZE = "1";
    private static final String ACCOMMODATION_AMENITIES = "Big";
    private static final BigDecimal ACCOMMODATION_PRICE = new BigDecimal("500");
    private static final Integer ACCOMMODATION_UNIT = 1;
    private static final Long BOOKING_ID = 1L;
    private static final LocalDate BOOKING_CHECK_IN = LocalDate.of(2024, 12, 19);
    private static final LocalDate BOOKING_CHECK_OUT = LocalDate.of(2024, 12, 24);
    private static final Long ACCOMMODATION_ID = 1L;
    private static List<Booking> bookings;
    private static List<BookingResponseDto> bookingResponseDtos;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeAll
    public static void beforeAll() {
        Address address = new Address();
        address.setId(ADDRESS_ID);
        address.setCity(ADDRESS_CITY);
        address.setCountry(ADDRESS_COUNTRY);
        address.setStreet(ADDRESS_STREET);
        address.setBuilding(ADDRESS_BUILDING);
        address.setFlat(ADDRESS_FLAT);
        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setEmail(CUSTOMER_EMAIL);
        customer.setPassword(CUSTOMER_PASSWORD);
        customer.setLastName(CUSTOMER_LAST_NAME);
        customer.setFirstName(CUSTOMER_FIRST_NAME);
        Accommodation accommodation = new Accommodation();
        accommodation.setAddress(address);
        accommodation.setSize(ACCOMMODATION_SIZE);
        accommodation.setAmenities(ACCOMMODATION_AMENITIES);
        accommodation.setPrice(ACCOMMODATION_PRICE);
        accommodation.setAvailableUnits(ACCOMMODATION_UNIT);
        Booking booking = new Booking();
        booking.setId(BOOKING_ID);
        booking.setCustomer(customer);
        booking.setAccommodation(accommodation);
        booking.setCheckIn(BOOKING_CHECK_IN);
        booking.setCheckOut(BOOKING_CHECK_OUT);
        booking.setStatus(Booking.Status.PENDING);
        bookings = List.of(booking);
        BookingResponseDto bookingResponseDto = new BookingResponseDto();
        bookingResponseDto.setStatus(Booking.Status.PENDING);
        bookingResponseDto.setId(BOOKING_ID);
        bookingResponseDto.setCheckIn(BOOKING_CHECK_IN);
        bookingResponseDto.setCheckOut(BOOKING_CHECK_OUT);
        bookingResponseDto.setAccommodationId(ACCOMMODATION_ID);
        bookingResponseDtos = List.of(bookingResponseDto);
    }

    @Test
    @DisplayName("""
            verify findAllByCustomerId() method work
            """)
    public void findAllByCustomerId() {
        BookingResponseDto bookingResponseDto = new BookingResponseDto();
        bookingResponseDto.setStatus(Booking.Status.PENDING);
        bookingResponseDto.setId(ACCOMMODATION_ID);
        bookingResponseDto.setCheckIn(BOOKING_CHECK_IN);
        bookingResponseDto.setCheckOut(BOOKING_CHECK_OUT);
        bookingResponseDto.setAccommodationId(ACCOMMODATION_ID);
        Long customerId = 1L;
        Pageable pageable = Pageable.ofSize(2);

        when(bookingRepository.findAllByCustomerId(customerId, pageable)).thenReturn(bookings);
        when(bookingMapper.toDtos(bookings)).thenReturn(bookingResponseDtos);
        List<BookingResponseDto> actual = bookingService.getAll(customerId, pageable);

        assertThat(actual).isEqualTo(bookingResponseDtos);
        verify(bookingRepository, times(1)).findAllByCustomerId(customerId, pageable);
        verifyNoMoreInteractions(bookingRepository, bookingMapper);
    }
}
