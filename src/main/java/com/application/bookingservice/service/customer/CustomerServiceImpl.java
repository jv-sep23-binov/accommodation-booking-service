package com.application.bookingservice.service.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    @Override
    public Object updateRole(Long id, Object updateRequestDto) {
        return null;
    }

    @Override
    public Object getById(Long customerId) {
        return null;
    }

    @Override
    public Object updateById(Long customerId, Object customerRequestDto) {
        return null;
    }
}
