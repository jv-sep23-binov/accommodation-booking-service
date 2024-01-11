package com.application.bookingservice.mapper;

import com.application.bookingservice.config.MapperConfig;
import com.application.bookingservice.dto.booking.BookingRequestDto;
import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.model.Booking;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookingMapper {
    BookingResponseDto toDto(Booking booking);

    Booking toEntity(BookingRequestDto requestDto);
}
