package com.application.bookingservice.service.accommodation;

import com.application.bookingservice.dto.accommodation.AccommodationRequestDto;
import com.application.bookingservice.dto.accommodation.AccommodationResponseDto;
import com.application.bookingservice.dto.accommodation.AccommodationUpdateRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AccommodationService {
    AccommodationResponseDto save(AccommodationRequestDto accommodationRequestDto);

    List<AccommodationResponseDto> getAll(Pageable pageable);

    AccommodationResponseDto findById(Long id);

    AccommodationResponseDto updateById(Long id, AccommodationUpdateRequestDto requestDto);

    void deleteById(Long id);
}
