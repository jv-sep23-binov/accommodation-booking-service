package com.application.bookingservice.service.customer;

import com.application.bookingservice.dto.customer.CustomerRegistrationRequestDto;
import com.application.bookingservice.dto.customer.CustomerRegistrationResponseDto;
import com.application.bookingservice.exception.RegistrationException;
import com.application.bookingservice.mapper.CustomerMapper;
import com.application.bookingservice.model.Customer;
import com.application.bookingservice.model.Role;
import com.application.bookingservice.repository.customer.CustomerRepository;
import com.application.bookingservice.repository.role.RoleRepository;
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
