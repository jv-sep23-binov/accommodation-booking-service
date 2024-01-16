package com.application.bookingservice.service.customer;

import com.application.bookingservice.dto.customer.CustomerRegistrationRequestDto;
import com.application.bookingservice.dto.customer.CustomerRegistrationResponseDto;
import com.application.bookingservice.dto.customer.CustomerResponseDto;
import com.application.bookingservice.dto.customer.CustomerResponseDtoWithRoles;
import com.application.bookingservice.dto.customer.CustomerUpdateRequestDto;
import com.application.bookingservice.dto.customer.CustomerUpdateResponseDto;
import com.application.bookingservice.dto.customer.CustomerUpdateRoleRequestDto;
import com.application.bookingservice.exception.EntityNotFoundException;
import com.application.bookingservice.exception.RegistrationException;
import com.application.bookingservice.mapper.CustomerMapper;
import com.application.bookingservice.model.Customer;
import com.application.bookingservice.model.Role;
import com.application.bookingservice.repository.customer.CustomerRepository;
import com.application.bookingservice.repository.role.RoleRepository;
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
    private static final String CUSTOMER_NOT_FOUND_MESSAGE
            = "Customer is not found with id: ";
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
                .orElseThrow(() -> new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE + id));
        Set<Role> newRoles = new HashSet<>(roleRepository
                .findAllById(updateRequestDto.getRoleIds()));
        existingCustomer.setRoles(newRoles);

        return customerMapper.toDtoWithRoles(customerRepository.save(existingCustomer));
    }

    @Override
    public CustomerResponseDto getById(Long customerId) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE
                        + customerId));
        return customerMapper.toResponseDto(customer);
    }

    @Override
    public CustomerUpdateResponseDto updateById(Long customerId,
                                          CustomerUpdateRequestDto customerUpdateRequestDto) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE
                        + customerId));
        customer.setEmail(customerUpdateRequestDto.getEmail());
        customer.setFirstName(customerUpdateRequestDto.getFirstName());
        customer.setLastName(customerUpdateRequestDto.getLastName());
        return customerMapper.toUpdateResponseDto(customerRepository.save(customer));
    }
}
