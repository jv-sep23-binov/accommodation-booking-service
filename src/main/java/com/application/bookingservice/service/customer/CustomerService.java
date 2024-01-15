package com.application.bookingservice.service.customer;

import com.application.bookingservice.dto.customer.CustomerRegistrationRequestDto;
import com.application.bookingservice.dto.customer.CustomerRegistrationResponseDto;
import com.application.bookingservice.dto.customer.CustomerResponseDto;
import com.application.bookingservice.dto.customer.CustomerResponseDtoWithRoles;
import com.application.bookingservice.dto.customer.CustomerUpdateRequestDto;
import com.application.bookingservice.dto.customer.CustomerUpdateResponseDto;
import com.application.bookingservice.dto.customer.CustomerUpdateRoleRequestDto;

public interface CustomerService {
    CustomerRegistrationResponseDto register(CustomerRegistrationRequestDto requestDto);

    CustomerResponseDtoWithRoles updateRole(Long id, CustomerUpdateRoleRequestDto updateRequestDto);

    CustomerResponseDto getById(Long customerId);

    CustomerUpdateResponseDto updateById(Long customerId,
                                         CustomerUpdateRequestDto customerUpdateRequestDto);
}
