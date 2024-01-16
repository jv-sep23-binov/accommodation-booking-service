package com.application.bookingservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.application.bookingservice.model.Customer;
import com.application.bookingservice.repository.customer.CustomerRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Find customer by email - Success")
    @Sql(scripts = {"classpath:db/customer/add-customers-to-customers-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:db/customer/delete-customers-from-customers-table.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findByEmail_Success() {
        String emailToFind = "test@gmail.com";

        Optional<Customer> foundCustomerOptional = customerRepository.findByEmail(emailToFind);

        assertTrue(foundCustomerOptional.isPresent());
        Customer foundCustomer = foundCustomerOptional.get();
        assertEquals(emailToFind, foundCustomer.getEmail());
    }

    @Test
    @DisplayName("Find customer by email - Not found")
    void findByEmail_NotFound() {
        Optional<Customer> foundCustomer = customerRepository.findByEmail("nonexistent@gmail.com");

        assertTrue(foundCustomer.isEmpty());
    }

    @Test
    @DisplayName("Find customer by id - Success")
    @Sql(scripts = {"classpath:db/customer/add-customers-to-customers-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:db/customer/delete-customers-from-customers-table.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findById_Success() {
        Long customerId = 4L;
        String expectedEmail = "test@gmail.com";

        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        assertTrue(customerOptional.isPresent());
        assertEquals(expectedEmail, customerOptional.get().getEmail());
    }

    @Test
    @DisplayName("Find customer by id - Not found")
    void findById_NotFound() {
        Optional<Customer> foundCustomer = customerRepository.findById(-1L);

        assertTrue(foundCustomer.isEmpty());
    }
}
