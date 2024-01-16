package com.application.bookingservice.repository.payment;

import static com.application.bookingservice.model.Payment.Status;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.application.bookingservice.model.Booking;
import com.application.bookingservice.model.Payment;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {
        "classpath:db/address/add-addresses.sql",
        "classpath:db/accommodation/add-accommodations.sql",
        "classpath:db/booking/add-bookings.sql",
        "classpath:db/payment/add-payments.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:db/payment/delete-payments.sql",
        "classpath:db/booking/delete-bookings.sql",
        "classpath:db/accommodation/delete-accommodations.sql",
        "classpath:db/address/delete-addresses.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PaymentRepositoryTest {
    private static Payment payment;
    @Autowired
    private PaymentRepository paymentRepository;

    @BeforeAll
    static void beforeAll() {
        Booking booking = new Booking();
        booking.setId(1L);

        payment = new Payment();
        payment.setId(1L);
        payment.setBooking(booking);
        payment.setStatus(Status.SUCCEED);
        payment.setTotal(new BigDecimal("100.00"));
        payment.setSessionUrl("Example");
        payment.setSessionId("hirsdHS_tjsjsyyjstIT");
    }

    @Test
    @DisplayName("Find payment by session id")
    void findBySessionId_ValidData_ReturnsCorrectPayment() {
        Optional<Payment> expected = Optional.of(payment);
        Optional<Payment> actual = paymentRepository.findBySessionId(payment.getSessionId());

        assertEquals(expected, actual,
                "Expected payment should be: " + expected
                        + " but was: " + actual
        );
    }

    @Test
    @DisplayName("Find payments by customer id")
    void findAllByCustomerId_ValidData_ReturnsCorrectPayments() {
        List<Payment> expected = List.of(payment);
        List<Payment> actual = paymentRepository.findAllByCustomerId(2L);

        assertEquals(expected.size(), actual.size(),
                "Expected payments quantity should be: " + expected
                        + " but was: " + actual
        );
        assertEquals(expected.get(0), actual.get(0),
                "Expected payment should be: " + expected.get(0)
                        + " but was: " + actual.get(0)
        );
    }
}
