package com.application.bookingservice.controller;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.application.bookingservice.dto.accommodation.AccommodationResponseDto;
import com.application.bookingservice.dto.address.AddressResponseDto;
import com.application.bookingservice.model.Accommodation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccommodationControllerTest {
    protected static MockMvc mockMvc;
    private static AddressResponseDto address1;
    private static AddressResponseDto address2;
    private static AccommodationResponseDto accommodation1;
    private static AccommodationResponseDto accommodation2;
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

        address1 = new AddressResponseDto()
                .setId(1L)
                .setCountry("Ukraine")
                .setCity("Lviv")
                .setStreet("Bohdan Khmelnytsky")
                .setBuilding(100);

        accommodation1 = new AccommodationResponseDto()
                .setId(1L)
                .setType(Accommodation.Type.HOUSE)
                .setAddress(address1)
                .setSize("100 sq.m.")
                .setAmenities("All")
                .setPrice(new BigDecimal("100.00"))
                .setAvailableUnits(1);

        address2 = new AddressResponseDto()
                .setId(2L)
                .setCountry("Ukraine")
                .setCity("Kyiv")
                .setStreet("Sichovykh Striltsiv")
                .setBuilding(15);

        accommodation2 = new AccommodationResponseDto()
                .setId(2L)
                .setType(Accommodation.Type.HOUSE)
                .setAddress(address2)
                .setSize("56 sq.m.")
                .setAmenities("All")
                .setPrice(new BigDecimal("300.00"))
                .setAvailableUnits(2);
    }

    @Test
    @WithMockUser(username = "customer", roles = {"CUSTOMER"})
    @Sql(scripts = "classpath:db/"
            + "accommodations/add-accommodations-and-addresses-to-tables.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/"
            + "accommodations/delete-accommodations-and-addresses-from-tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAll_GivenTwoAccommodations_ReturnAll() throws Exception {
        List<AccommodationResponseDto> expected = List.of(accommodation1, accommodation2);

        MvcResult result = mockMvc.perform(get("/accommodations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<AccommodationResponseDto> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<AccommodationResponseDto>>() {});

        assertIterableEquals(expected, actual, "Expected accommodations should be: " + expected
                + " but was: " + actual);
    }
}
