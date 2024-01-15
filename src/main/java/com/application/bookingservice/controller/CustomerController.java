package com.application.bookingservice.controller;

import com.application.bookingservice.dto.customer.CustomerDto;
import com.application.bookingservice.dto.customer.CustomerResponseDtoWithRoles;
import com.application.bookingservice.dto.customer.CustomerUpdateRoleRequestDto;
import com.application.bookingservice.model.Customer;
import com.application.bookingservice.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
@Tag(name = "Customer management.",
        description = "Endpoints for managing customers.")
public class CustomerController {
    private final CustomerService customerService;

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PutMapping("/{id}/role")
    @Operation(summary = "Update roles.",
            description = "Enables customers to update their roles, providing role-based access.")
    public CustomerResponseDtoWithRoles updateRole(@PathVariable Long id,
                                                   @RequestBody @Valid CustomerUpdateRoleRequestDto
                                     updateCustomerRoleRequestDto) {
        return customerService.updateRole(id, updateCustomerRoleRequestDto);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/me")
    @Operation(summary = "Get customer.",
            description = "Retrieves the profile information for the currently logged-in customer.")
    public CustomerDto getCustomer(Authentication authentication) {
        Customer customer = (Customer) authentication.getPrincipal();
        return customerService.getById(customer.getId());
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PutMapping("/me")
    @Operation(summary = "Update profile information.",
            description = "Allows customers to update their profile information.")
    public CustomerDto updateProfileInfo(@RequestBody @Valid CustomerDto customerDto,
                                         Authentication authentication) {
        Customer customer = (Customer) authentication.getPrincipal();
        return customerService.updateById(customer.getId(), customerDto);
    }
}
