package com.application.bookingservice.service.customer;

import com.application.bookingservice.dto.customer.CustomerRegistrationRequestDto;
import com.application.bookingservice.dto.customer.CustomerRegistrationResponseDto;

public interface CustomerService {
    CustomerRegistrationResponseDto register(CustomerRegistrationRequestDto requestDto);

    Object updateRole(Long id, Object updateRequestDto);

    Object getById(Long customerId);

    Object updateById(Long customerId, Object customerRequestDto);
}
