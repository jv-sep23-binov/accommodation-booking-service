package com.application.bookingservice.mapper;

import com.application.bookingservice.config.MapperConfig;
import com.application.bookingservice.dto.customer.CustomerRegistrationRequestDto;
import com.application.bookingservice.dto.customer.CustomerRegistrationResponseDto;
import com.application.bookingservice.dto.customer.CustomerResponseDto;
import com.application.bookingservice.dto.customer.CustomerResponseDtoWithRoles;
import com.application.bookingservice.dto.customer.CustomerUpdateResponseDto;
import com.application.bookingservice.model.Customer;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CustomerMapper {
    CustomerRegistrationResponseDto toDto(Customer customer);

    Customer toEntity(CustomerRegistrationRequestDto requestDto);

    CustomerResponseDto toResponseDto(Customer customer);

    CustomerUpdateResponseDto toUpdateResponseDto(Customer customer);

    CustomerResponseDtoWithRoles toDtoWithRoles(Customer customer);
}
