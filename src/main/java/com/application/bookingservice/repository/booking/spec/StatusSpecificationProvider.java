package com.application.bookingservice.repository.booking.spec;

import com.application.bookingservice.model.Booking;
import com.application.bookingservice.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class StatusSpecificationProvider implements SpecificationProvider<Booking> {
    private static final String STATUS = "status";

    @Override
    public String getKey() {
        return STATUS;
    }

    public Specification<Booking> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> root.get(STATUS).in((Object[]) params);
    }
}
