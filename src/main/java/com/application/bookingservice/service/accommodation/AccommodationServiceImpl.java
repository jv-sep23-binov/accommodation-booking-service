package com.application.bookingservice.service.accommodation;

import java.util.List;

import com.application.bookingservice.dto.accommodation.AccommodationRequestDto;
import com.application.bookingservice.dto.accommodation.AccommodationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {
    @Override
    public AccommodationResponseDto save(AccommodationRequestDto accommodationRequestDto) {
        return null;
    }

    @Override
    public List<AccommodationResponseDto> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public AccommodationResponseDto findById(Long id) {
        return null;
    }

    @Override
    public AccommodationResponseDto updateById(Long id, AccommodationRequestDto accommodationRequestDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
