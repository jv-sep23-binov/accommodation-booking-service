package com.application.bookingservice.service.accommodation;

import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AccommodationService {
    Object save(Object accommodationRequestDto);

    List<Object> getAll(Pageable pageable);

    Object findById(Long id);

    Object updateById(Long id, Object updateAccommodationRequestDto);

    void deleteById(Long id);
}
