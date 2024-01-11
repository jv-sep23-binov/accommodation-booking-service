package com.application.bookingservice.mapper;

import com.application.bookingservice.config.MapperConfig;
import com.application.bookingservice.dto.booking.BookingRequestDto;
import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.dto.booking.BookingUpdateRequestDto;
import com.application.bookingservice.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface BookingMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "accommodationId", source = "accommodation.id")
    BookingResponseDto toDto(Booking booking);

    @Mapping(target = "accommodation.id", source = "accommodationId")
    Booking toEntity(BookingRequestDto requestDto);

    Booking toEntity(BookingUpdateRequestDto requestDto);
}
