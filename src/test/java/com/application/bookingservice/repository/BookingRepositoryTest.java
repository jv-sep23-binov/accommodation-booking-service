package com.application.bookingservice.repository;

import com.application.bookingservice.model.Accommodation;
import com.application.bookingservice.model.Address;
import com.application.bookingservice.model.Booking;
import com.application.bookingservice.model.Customer;
import com.application.bookingservice.repository.booking.BookingRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookingRepositoryTest {
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
    @Autowired
    private BookingRepository bookingRepository;

    @Sql(scripts = {"classpath:database/booking/insert-addresses-table.sql",
            "classpath:database/booking/insert-accommodations-table.sql",
            "classpath:database/booking/insert-bookings-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/booking/clear-tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("""
            find bookings by customer Id
            """)
    public void findAllByCustomerId_validData_Success() {
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
        List<Booking> expected = List.of(booking);

        Pageable pageable = Pageable.ofSize(1);
        List<Booking> actual = bookingRepository.findAllByCustomerId(CUSTOMER_ID, pageable);

        Assertions.assertEquals(expected, actual);
    }
}
