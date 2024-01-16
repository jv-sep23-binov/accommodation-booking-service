package com.application.bookingservice.controller;

import static com.application.bookingservice.model.Booking.Status.PENDING;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.model.Customer;
import com.application.bookingservice.model.Role;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
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

@Sql(scripts = {"classpath:database/booking/insert-addresses-table.sql",
        "classpath:database/booking/insert-accommodations-table.sql",
        "classpath:database/booking/insert-bookings-table.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingControllerTest {
    protected static MockMvc mockMvc;
    private static final String MY_BOOKING_API = "/bookings/my";
    private static final Long ACCOMMODATION_ID = 1L;
    private static final LocalDate BOOKING_CHECK_OUT = LocalDate.of(2024, 12, 24);
    private static final LocalDate BOOKING_CHECK_IN = LocalDate.of(2024, 12, 19);
    private static final Long BOOKING_ID = 1L;
    private static final Long CUSTOMER_ID = 1L;
    private static final String CUSTOMER_EMAIL = "coolemail@gmai.com";
    private static final String CUSTOMER_PASSWORD = "hardpassword";
    private static final Long ROLE_ID = 1L;
    private static List<BookingResponseDto> bookingResponseDtos;
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
        BookingResponseDto booking = new BookingResponseDto();
        booking.setAccommodationId(ACCOMMODATION_ID);
        booking.setCheckOut(BOOKING_CHECK_OUT);
        booking.setCheckIn(BOOKING_CHECK_IN);
        booking.setStatus(PENDING);
        booking.setId(BOOKING_ID);
        booking.setCustomerId(CUSTOMER_ID);
        bookingResponseDtos = List.of(booking);
    }

    @BeforeEach
     void setUp() {
        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setEmail(CUSTOMER_EMAIL);
        customer.setPassword(CUSTOMER_PASSWORD);
        Role role = new Role();
        role.setId(ROLE_ID);
        role.setRole(Role.RoleName.ROLE_CUSTOMER);
        customer.setRoles(Set.of(role));
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(customer,
                customer.getPassword(), customer.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @WithMockUser(username = "Customer", roles = {"CUSTOMER"})
    @Test
    @DisplayName("""
            verify get my booking work
            """)
    public void getAll_ValidData_Success() throws Exception {
        List<BookingResponseDto> expected = new ArrayList<>(bookingResponseDtos);

        //When
        MvcResult result = mockMvc.perform(
                get(MY_BOOKING_API)
        ).andReturn();

        //then
        List<BookingResponseDto> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<BookingResponseDto>>() {}
        );
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
    }
}
