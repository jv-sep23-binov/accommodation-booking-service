package com.application.bookingservice.service.accommodation;

import com.application.bookingservice.dto.accommodation.AccommodationRequestDto;
import com.application.bookingservice.dto.accommodation.AccommodationResponseDto;
import com.application.bookingservice.exception.EntityNotFoundException;
import com.application.bookingservice.mapper.AccommodationMapper;
import com.application.bookingservice.model.Accommodation;
import com.application.bookingservice.repository.accommodation.AccommodationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final AccommodationMapper accommodationMapper;

    @Override
    public AccommodationResponseDto save(AccommodationRequestDto accommodationRequestDto) {
        return null;
    }

    @Override
    public List<AccommodationResponseDto> getAll(Pageable pageable) {
        return accommodationRepository.getAll(pageable)
                .stream()
                .map(accommodationMapper::toDto)
                .toList();
    }

    @Override
    public AccommodationResponseDto findById(Long id) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find accommodation with id: " + id));
        return accommodationMapper.toDto(accommodation);
    }

    @Override
    public AccommodationResponseDto updateById(Long id,
                                               AccommodationRequestDto accommodationRequestDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
