package com.application.bookingservice.service.customer;

import com.application.bookingservice.dto.customer.CustomerDto;
import com.application.bookingservice.dto.customer.CustomerRegistrationRequestDto;
import com.application.bookingservice.dto.customer.CustomerRegistrationResponseDto;
import com.application.bookingservice.dto.customer.CustomerResponseDtoWithRoles;
import com.application.bookingservice.dto.customer.CustomerUpdateRoleRequestDto;

public interface CustomerService {
    CustomerRegistrationResponseDto register(CustomerRegistrationRequestDto requestDto);

    CustomerResponseDtoWithRoles updateRole(Long id, CustomerUpdateRoleRequestDto updateRequestDto);

    CustomerDto getById(Long customerId);

    CustomerDto updateById(Long customerId, CustomerDto customerDto);
}
