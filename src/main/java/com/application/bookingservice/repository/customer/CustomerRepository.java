package com.application.bookingservice.repository.customer;

import com.application.bookingservice.model.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c JOIN FETCH c.roles r WHERE c.email = :email")
    Optional<Customer> findByEmail(String email);
}