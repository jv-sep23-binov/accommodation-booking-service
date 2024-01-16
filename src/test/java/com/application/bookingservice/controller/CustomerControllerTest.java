package com.application.bookingservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.application.bookingservice.dto.customer.CustomerResponseDto;
import com.application.bookingservice.dto.customer.CustomerUpdateRequestDto;
import com.application.bookingservice.dto.customer.CustomerUpdateResponseDto;
import com.application.bookingservice.model.Customer;
import com.application.bookingservice.model.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class CustomerControllerTest {
    private static CustomerResponseDto responseDto;
    private static MockMvc mockMvc;
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

        responseDto = new CustomerResponseDto();
        responseDto.setEmail("alice@example.com");
        responseDto.setFirstName("Alice");
        responseDto.setLastName("Smith");
    }

    @Sql(scripts = {"classpath:db/customer/add-customers-to-customers-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setId(2L);
        role.setRole(Role.RoleName.ROLE_CUSTOMER);
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setEmail("alice@example.com");
        customer.setFirstName("Alice");
        customer.setLastName("Smith");
        customer.setPassword("12345678");
        customer.setRoles(Set.of(role));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                customer,
                customer.getPassword(),
                customer.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    @Sql(scripts = {"classpath:db/customer/delete-customers-from-customers-table.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void cleanUpEach() {

    }

    @Test
    @WithMockUser(username = "customer", roles = {"CUSTOMER"})
    void getCustomer_ValidRequest_ReturnsCorrectResponseDto() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/customers/me"))
                .andReturn();

        CustomerResponseDto expected = responseDto;
        CustomerResponseDto actual = objectMapper.readValue(mvcResult.getResponse()
                        .getContentAsString(),
                CustomerResponseDto.class);

        assertEquals(expected, actual,
                "Expected should be: " + expected
                + " but was " + actual
        );

    }

    @Test
    @WithMockUser(username = "customer", roles = {"CUSTOMER"})
    void updateProfileInfo_ValidRequest_ReturnsCorrectResponseDto() throws Exception {
        CustomerUpdateRequestDto updateRequestDto = new CustomerUpdateRequestDto();
        updateRequestDto.setFirstName("UpdatedFirstName");
        updateRequestDto.setLastName("UpdatedLastName");

        MvcResult mvcResult = mockMvc.perform(put("/customers/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDto)))
                .andReturn();

        CustomerUpdateResponseDto expected = new CustomerUpdateResponseDto();

        CustomerUpdateResponseDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                CustomerUpdateResponseDto.class);

        assertEquals(expected, actual,
                "Expected should be: " + expected
                        + " but was " + actual
        );
    }

    @Test
    @WithMockUser(username = "customer", roles = {"CUSTOMER"})
    void updateProfileInfo_InvalidRequest_ReturnsBadRequest() throws Exception {
        CustomerUpdateRequestDto invalidUpdateRequestDto = new CustomerUpdateRequestDto();

        MvcResult mvcResult = mockMvc.perform(put("/customers/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUpdateRequestDto)))
                .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(),
                "Expected HTTP status should be Bad Request");
    }
}
