package com.application.bookingservice.service.customer;

public interface CustomerService {
    Object updateRole(Long id, Object updateRequestDto);

    Object getById(Long customerId);

    Object updateById(Long customerId, Object customerRequestDto);
}
