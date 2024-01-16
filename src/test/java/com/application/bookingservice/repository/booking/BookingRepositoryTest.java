package com.application.bookingservice.repository.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.application.bookingservice.model.Accommodation;
import com.application.bookingservice.model.Booking;
import com.application.bookingservice.model.Customer;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
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
    private static final Long CUSTOMER_ID = 1L;
    private static final Long ACCOMMODATION_ID = 1L;
    private static final Long BOOKING_ID = 1L;
    private static final LocalDate BOOKING_CHECK_IN = LocalDate.of(2024, 12, 19);
    private static final LocalDate BOOKING_CHECK_OUT = LocalDate.of(2024, 12, 24);
    private static Booking booking;
    @Autowired
    private BookingRepository bookingRepository;

    @BeforeAll
    static void beforeAll() {
        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);
        Accommodation accommodation = new Accommodation();
        accommodation.setId(ACCOMMODATION_ID);
        booking = new Booking();
        booking.setId(BOOKING_ID);
        booking.setCustomer(customer);
        booking.setAccommodation(accommodation);
        booking.setCheckIn(BOOKING_CHECK_IN);
        booking.setCheckOut(BOOKING_CHECK_OUT);
        booking.setStatus(Booking.Status.PENDING);
    }

    @Sql(scripts = {"classpath:db/booking/insert-addresses-table.sql",
            "classpath:db/booking/insert-accommodations-table.sql",
            "classpath:db/booking/insert-bookings-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/booking/clear-tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    @DisplayName("""
            find bookings by customer Id
            """)
    public void findAllByCustomerId_validData_Success() {
        List<Booking> expected = List.of(booking);

        Pageable pageable = Pageable.ofSize(1);
        List<Booking> actual = bookingRepository.findAllByCustomerId(CUSTOMER_ID, pageable);

        assertEquals(expected, actual);
    }
}
