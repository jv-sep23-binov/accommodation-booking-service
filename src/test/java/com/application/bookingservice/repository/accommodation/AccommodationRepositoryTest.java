package com.application.bookingservice.repository.accommodation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.application.bookingservice.model.Accommodation;
import com.application.bookingservice.model.Address;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccommodationRepositoryTest {
    @Autowired
    private AccommodationRepository accommodationRepository;

    @Test
    @Sql(scripts = "classpath:db/"
            + "accommodations/add-accommodations-and-addresses-to-tables.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/"
            + "accommodations/delete-accommodations-and-addresses-from-tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
                .setPrice(new BigDecimal("100.00"))
                .setAvailableUnits(1);

        Accommodation actual = accommodationRepository.findById(1L).get();

        assertEquals(expected, actual, "Expected accommodation should be: " + expected
                + " but was: " + actual);
    }
}
