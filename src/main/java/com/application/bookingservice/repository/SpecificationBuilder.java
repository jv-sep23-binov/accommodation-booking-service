package com.application.bookingservice.repository;

import com.application.bookingservice.dto.booking.BookingSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookingSearchParametersDto searchParameters);
}
