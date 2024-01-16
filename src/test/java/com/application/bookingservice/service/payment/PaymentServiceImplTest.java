package com.application.bookingservice.service.payment;

import static com.application.bookingservice.model.Payment.Status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.application.bookingservice.dto.payment.PaymentResponseDto;
import com.application.bookingservice.mapper.PaymentMapper;
import com.application.bookingservice.model.Booking;
import com.application.bookingservice.model.Payment;
import com.application.bookingservice.repository.payment.PaymentRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @Mock
    private static PaymentRepository paymentRepository;
    @Mock
    private static PaymentMapper paymentMapper;
    @InjectMocks
    private static PaymentServiceImpl paymentService;
    private static Payment payment1;
    private static Payment payment2;
    private static PaymentResponseDto dto1;
    private static PaymentResponseDto dto2;

    @BeforeAll
    static void beforeAll() {
        payment1 = new Payment();
        payment1.setId(1L);
        payment1.setStatus(Status.SUCCEED);
        Booking booking1 = new Booking();
        booking1.setId(456L);
        payment1.setBooking(booking1);
        payment1.setTotal(new BigDecimal("100.00"));

        payment2 = new Payment();
        payment2.setId(2L);
        payment2.setStatus(Status.FAILED);
        Booking booking2 = new Booking();
        booking1.setId(789L);
        payment2.setBooking(booking2);
        payment2.setTotal(new BigDecimal("50.00"));

        dto1 = new PaymentResponseDto();
        dto1.setId(1L);
        dto1.setStatus(Status.SUCCEED);
        dto1.setBookingId(456L);
        dto1.setTotal(new BigDecimal("100.00"));

        dto2 = new PaymentResponseDto();
        dto2.setId(2L);
        dto2.setStatus(Status.FAILED);
        dto2.setBookingId(789L);
        dto2.setTotal(new BigDecimal("50.00"));
    }

    @Test
    @DisplayName("Get payments by customer id")
    public void getPaymentsByCustomerId_ValidData_ReturnsDtoList() {
        Long customerId = 1L;

        List<Payment> mockPayments = Arrays.asList(payment1, payment2);

        when(paymentRepository.findAllByCustomerId(customerId)).thenReturn(mockPayments);
        when(paymentMapper.toDto(payment1)).thenReturn(dto1);
        when(paymentMapper.toDto(payment2)).thenReturn(dto2);

        List<PaymentResponseDto> result = paymentService.getPaymentsByCustomerId(customerId);

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));

        verify(paymentRepository).findAllByCustomerId(customerId);
        verify(paymentMapper, times(2)).toDto(any());
    }
}
