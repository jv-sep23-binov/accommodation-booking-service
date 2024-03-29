package com.application.bookingservice.mapper;

import com.application.bookingservice.config.MapperConfig;
import com.application.bookingservice.dto.address.AddressRequestDto;
import com.application.bookingservice.dto.address.AddressResponseDto;
import com.application.bookingservice.model.Address;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface AddressMapper {
    Address toEntity(AddressRequestDto requestDto);

    AddressResponseDto toDto(Address address);
}
