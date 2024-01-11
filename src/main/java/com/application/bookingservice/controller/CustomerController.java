package com.application.bookingservice.controller;

import com.application.bookingservice.service.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private CustomerService customerService;

    @PutMapping("/{id}/role")
    public Object updateRole(@PathVariable Long id,
                             @RequestBody @Valid Object updateCustomerRoleRequestDto) {
        return customerService.updateRole(id, updateCustomerRoleRequestDto);
    }

    @GetMapping("/me")
    public Object getCustomer() {
        //Customer customer = (Customer) authentication.getPrincipal();
        Long customerId = 1L;
        return customerService.getById(customerId);
    }

    @PutMapping("/me")
    public Object updateProfileInfo(@RequestBody @Valid Object customerRequestDto) {
        //Customer customer = (Customer) authentication.getPrincipal();
        Long customerId = 1L;
        return customerService.updateById(customerId, customerRequestDto);
    }
}
