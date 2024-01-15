package com.application.bookingservice.repository.accommodation;

import com.application.bookingservice.model.Accommodation;
import com.application.bookingservice.model.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccommodationRepositoryTest {
    @Autowired
    private AccommodationRepository accommodationRepository;

    @Test
    @Sql(scripts = "classpath:database/accommodations/add-accommodation-and-address-to-tables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/accommodations/delete-accommodation-and-address-from-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findById_AccommodationWithAddress_ReturnAccommodation() {
        Address address = new Address()
                .setId(1L)
                .setCountry("Ukraine")
                .setCity("Lviv")
                .setStreet("Bohdan Khmelnytsky")
                .setBuilding(100);

        Accommodation expected = new Accommodation()
                .setId(1L)
                .setType(Accommodation.Type.HOUSE)
                .setAddress(address)
                .setSize("100 sq.m.")
                .setAmenities("All")
                .setPrice(BigDecimal.valueOf(100))
                .setAvailableUnits(1);

        Accommodation actual = accommodationRepository.findById(1l).get();

        EqualsBuilder.reflectionEquals(expected, actual, "price");
    }
}