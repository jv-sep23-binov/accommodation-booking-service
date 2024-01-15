package com.application.bookingservice.service.customer;

import com.application.bookingservice.dto.customer.CustomerDto;
import com.application.bookingservice.dto.customer.CustomerRegistrationRequestDto;
import com.application.bookingservice.dto.customer.CustomerRegistrationResponseDto;
import com.application.bookingservice.dto.customer.CustomerResponseDtoWithRoles;
import com.application.bookingservice.dto.customer.CustomerUpdateRoleRequestDto;
import com.application.bookingservice.exception.RegistrationException;
import com.application.bookingservice.mapper.CustomerMapper;
import com.application.bookingservice.model.Customer;
import com.application.bookingservice.model.Role;
import com.application.bookingservice.repository.customer.CustomerRepository;
import com.application.bookingservice.repository.role.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private static final String CUSTOMER_ALREADY_REGISTERED_MESSAGE
            = "Customer is already exist";
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomerRegistrationResponseDto register(CustomerRegistrationRequestDto requestDto) {
        if (customerRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException(CUSTOMER_ALREADY_REGISTERED_MESSAGE);
        }

        Customer savedCustomer = customerMapper.toModel(requestDto);
        Role customerRole = roleRepository.findByRole(Role.RoleName.ROLE_CUSTOMER);
        savedCustomer.setRoles(Set.of(customerRole));
        savedCustomer.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        return customerMapper.toDto(customerRepository.save(savedCustomer));
    }

    @Override
    public CustomerResponseDtoWithRoles updateRole(Long id,
                                                   CustomerUpdateRoleRequestDto updateRequestDto) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer is "
                        + "not found with id: " + id));
        Set<Role> newRoles = new HashSet<>(roleRepository
                .findAllById(updateRequestDto.getRoleIds()));
        existingCustomer.setRoles(newRoles);

        return customerMapper.toCustomerResponseDto(customerRepository.save(existingCustomer));
    }

    @Override
    public CustomerDto getById(Long customerId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer is not found with id: "
                        + customerId));
        return customerMapper.toCustomerDto(customer);
    }

    @Override
    public CustomerDto updateById(Long customerId, CustomerDto customerDto) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: "
                        + customerId));
        customer.setEmail(customerDto.getEmail());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        return customerMapper.toCustomerDto(customerRepository.save(customer));
    }
}
