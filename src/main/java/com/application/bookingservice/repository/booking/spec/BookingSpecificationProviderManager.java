package com.application.bookingservice.repository.booking.spec;

import com.application.bookingservice.model.Booking;
import com.application.bookingservice.repository.SpecificationProvider;
import com.application.bookingservice.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingSpecificationProviderManager implements SpecificationProviderManager<Booking> {
    private static final String CANNOT_FIND_SPECIFICATION_BY_KEY
            = "Can't find specification by key: ";
    private final List<SpecificationProvider<Booking>> bookingSpecificationProviders;

    @Override
    public SpecificationProvider<Booking> getSpecificationProvider(String key) {
        return bookingSpecificationProviders.stream()
                .filter(spec -> spec.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(CANNOT_FIND_SPECIFICATION_BY_KEY + key));
    }
}
