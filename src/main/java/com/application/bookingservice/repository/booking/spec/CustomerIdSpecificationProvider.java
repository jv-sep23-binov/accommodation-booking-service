package com.application.bookingservice.repository.booking.spec;

import com.application.bookingservice.model.Booking;
import com.application.bookingservice.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CustomerIdSpecificationProvider implements SpecificationProvider<Booking> {
    private static final String CUSTOMER_ID = "customer_id";

    @Override
    public String getKey() {
        return CUSTOMER_ID;
    }

    public Specification<Booking> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> root.join("customer").get("id").in(params);
    }
}
