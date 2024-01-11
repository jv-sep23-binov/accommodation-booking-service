package com.application.bookingservice.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {
    @Override
    public Object save(Object accommodationRequestDto) {
        return null;
    }

    @Override
    public List<Object> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public Object findById(Long id) {
        return null;
    }

    @Override
    public Object updateById(Long id, Object updateAccommodationRequestDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
