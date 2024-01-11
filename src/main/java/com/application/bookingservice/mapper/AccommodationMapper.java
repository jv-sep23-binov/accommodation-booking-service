package com.application.bookingservice.mapper;

import com.application.bookingservice.config.MapperConfig;
import com.application.bookingservice.dto.accommodation.AccommodationResponseDto;
import com.application.bookingservice.dto.accommodation.AccommodationRequestDto;
import com.application.bookingservice.model.Accommodation;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface AccommodationMapper {
    AccommodationResponseDto toDto(Accommodation accommodation);

    Accommodation toEntity(AccommodationRequestDto requestDto);
}
