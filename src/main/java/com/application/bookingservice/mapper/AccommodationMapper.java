package com.application.bookingservice.mapper;

import com.application.bookingservice.config.MapperConfig;
import com.application.bookingservice.dto.accommodation.AccommodationRequestDto;
import com.application.bookingservice.dto.accommodation.AccommodationResponseDto;
import com.application.bookingservice.model.Accommodation;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(config = MapperConfig.class, uses = AddressMapper.class)
public interface AccommodationMapper {
    @Named("toDto")
    AccommodationResponseDto toDto(Accommodation accommodation);

    Accommodation toEntity(AccommodationRequestDto requestDto);

    @IterableMapping(qualifiedByName = "toDto")
    List<AccommodationResponseDto> toDtos(List<Accommodation> accommodations);
}
