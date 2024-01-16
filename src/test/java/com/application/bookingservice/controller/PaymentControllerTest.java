package com.application.bookingservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.application.bookingservice.dto.payment.PaymentResponseDto;
import com.application.bookingservice.model.Customer;
import com.application.bookingservice.model.Payment;
import com.application.bookingservice.model.Role;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerTest {
    private static MockMvc mockMvc;
    private static PaymentResponseDto responseDto;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();

        responseDto = new PaymentResponseDto();
        responseDto.setId(1L);
        responseDto.setStatus(Payment.Status.SUCCEED);
        responseDto.setBookingId(1L);
        responseDto.setTotal(new BigDecimal("100.00"));
    }

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setId(1L);
        role.setRole(Role.RoleName.ROLE_CUSTOMER);
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setEmail("alice@example.com");
        customer.setPassword("12345678");
        customer.setRoles(Set.of(role));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                customer,
                customer.getPassword(),
                customer.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @WithMockUser(username = "alice@example.com", roles = {"CUSTOMER"})
    @Test
    @DisplayName("Get user's payments")
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
    void getPaymentsByUserId_ValidRequest_ReturnsCorrectResponseDto() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/payments")).andReturn();

        PaymentResponseDto expected = responseDto;
        List<PaymentResponseDto> actual = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(),
                        new TypeReference<>() {
                        });

        assertEquals(1, actual.size(),
                "Expected payments amount should be: " + expected
                        + " but was: " + actual
        );
        assertEquals(expected, actual.get(0),
                "Expected payment should be: " + expected
                        + " but was: " + actual
        );
    }
}
