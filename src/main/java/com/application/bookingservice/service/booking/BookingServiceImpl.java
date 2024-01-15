package com.application.bookingservice.service.booking;

import com.application.bookingservice.dto.booking.BookingRequestDto;
import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.dto.booking.BookingSearchParametersDto;
import com.application.bookingservice.dto.booking.BookingUpdateRequestDto;
import com.application.bookingservice.mapper.BookingMapper;
import com.application.bookingservice.model.Booking;
import com.application.bookingservice.repository.booking.BookingRepository;
import com.application.bookingservice.repository.booking.spec.BookingSpecificationBuilder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingSpecificationBuilder specificationBuilder;
    private final BookingMapper bookingMapper;

    @Override
    public BookingResponseDto save(Long customerId, BookingRequestDto requestBookingDto) {
        return null;
    }

    @Override
    public List<BookingResponseDto> getAll(Long customerId, Pageable pageable) {
        return null;
    }

    @Override
    public BookingResponseDto findById(Long id) {
        return null;
    }

    @Override
    public BookingResponseDto updateById(Long id, BookingUpdateRequestDto bookingUpdateRequestDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<BookingResponseDto> search(
            BookingSearchParametersDto searchParameters
    ) {
        Specification<Booking> specification = specificationBuilder.build(searchParameters);
        return bookingRepository.findAll(specification).stream()
                .map(bookingMapper::toDto)
                .toList();
    }
}
