package com.application.bookingservice.repository.booking.spec;

import com.application.bookingservice.dto.booking.BookingSearchParametersDto;
import com.application.bookingservice.model.Booking;
import com.application.bookingservice.repository.SpecificationBuilder;
import com.application.bookingservice.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingSpecificationBuilder implements SpecificationBuilder<Booking> {
    private final SpecificationProviderManager<Booking> specificationProviderManager;

    @Override
    public Specification<Booking> build(BookingSearchParametersDto searchParameters) {
        Specification<Booking> spec = Specification.where(null);

        if (searchParameters.customerIds() != null && searchParameters.customerIds().length > 0) {
            spec = spec.and(specificationProviderManager.getSpecificationProvider("customer_id")
                    .getSpecification(searchParameters.customerIds()));
        }

        if (searchParameters.statuses() != null && searchParameters.statuses().length > 0) {
            spec = spec.and(specificationProviderManager.getSpecificationProvider("status")
                    .getSpecification(searchParameters.statuses()));
        }

        return spec;
    }
}
