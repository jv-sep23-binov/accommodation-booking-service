package com.application.bookingservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.application.bookingservice.mapper.impl.CustomerMapperImpl;
import com.application.bookingservice.model.Customer;
import com.application.bookingservice.model.Role;
import com.application.bookingservice.repository.customer.CustomerRepository;
import com.application.bookingservice.repository.role.RoleRepository;
import com.application.bookingservice.service.customer.CustomerServiceImpl;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    private static final Long EXISTING_CUSTOMER_ID = 1L;
    private static final Long NON_EXISTENT_CUSTOMER_ID = 99L;
    private static final String EXISTING_EMAIL = "existing@gmail.com";
    private static final String NEW_EMAIL = "new@gmail.com";
    private static final String CUSTOMER_ALREADY_REGISTERED_MESSAGE
            = "Customer is already exist";
    private static final String CUSTOMER_NOT_FOUND_MESSAGE
            = "Customer is not found with id: ";
    private static Customer alice;
    private static Customer bob;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;
    @Spy
    private CustomerMapper customerMapper = new CustomerMapperImpl();
    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeAll
    static void beforeAll() {
        alice = new Customer();
        alice.setId(1L);
        alice.setEmail("alice@gmail.com");
        alice.setFirstName("Alice");
        alice.setLastName("Smith");
        alice.setPassword("123456789");
        alice.setIsDeleted(false);
        bob = new Customer();
        bob.setId(2L);
        bob.setEmail("bob@gmail.com");
        bob.setFirstName("Bob");
        bob.setLastName("Jonson");
        bob.setPassword("987654321");
        bob.setIsDeleted(false);
    }

    @Test
    @DisplayName("Get customer by id - Customer found")
    void getById_ExistingId_EntityNotFoundExceptionExpected() {
        when(customerRepository.findById(EXISTING_CUSTOMER_ID)).thenReturn(Optional.of(alice));
        CustomerResponseDto testDto = new CustomerResponseDto();
        testDto.setEmail(alice.getEmail());
        testDto.setFirstName(alice.getFirstName());
        testDto.setLastName(alice.getLastName());
        when(customerMapper.toResponseDto(alice)).thenReturn(testDto);

        CustomerResponseDto result = customerService.getById(EXISTING_CUSTOMER_ID);

        assertNotNull(result);
        verify(customerRepository, times(1)).findById(EXISTING_CUSTOMER_ID);
        verify(customerMapper, times(1)).toResponseDto(alice);
        assertEquals(alice.getFirstName(), result.getFirstName());
        assertEquals(alice.getEmail(), result.getEmail());
    }

    @Test
    @DisplayName("Get customer by id - Customer not found")
    void getById_NotExistingId_EntityNotFoundExceptionExpected() {
        when(customerRepository.findById(NON_EXISTENT_CUSTOMER_ID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> customerService.getById(NON_EXISTENT_CUSTOMER_ID)
        );

        assertEquals(CUSTOMER_NOT_FOUND_MESSAGE
                + NON_EXISTENT_CUSTOMER_ID, exception.getMessage());

        verify(customerRepository, times(1)).findById(NON_EXISTENT_CUSTOMER_ID);
        verify(customerMapper, never()).toResponseDto(any());
        assertTrue(exception.getMessage().contains(String.valueOf(NON_EXISTENT_CUSTOMER_ID)));
    }

    @Test
    @DisplayName("Update customer by id - Alice updated")
    void updateById_AliceUpdated_ReturnsUpdateResponseDto() {
        CustomerUpdateRequestDto updateRequestDto = new CustomerUpdateRequestDto();
        updateRequestDto.setEmail("updated_alice@gmail.com");
        updateRequestDto.setFirstName("UpdatedAlice");
        updateRequestDto.setLastName("UpdatedSmith");
        CustomerUpdateResponseDto customerUpdateResponseDto = new CustomerUpdateResponseDto();
        customerUpdateResponseDto.setEmail(updateRequestDto.getEmail());
        customerUpdateResponseDto.setFirstName(updateRequestDto.getFirstName());
        customerUpdateResponseDto.setLastName(updateRequestDto.getLastName());
        when(customerRepository.findById(alice.getId())).thenReturn(Optional.of(alice));
        when(customerRepository.save(any())).thenReturn(alice);
        when(customerMapper.toUpdateResponseDto(alice)).thenReturn(customerUpdateResponseDto);

        CustomerUpdateResponseDto result = customerService.updateById(alice.getId(),
                updateRequestDto);

        assertEquals(alice.getEmail(), result.getEmail());
        assertEquals(updateRequestDto.getEmail(), result.getEmail());
        assertEquals(updateRequestDto.getFirstName(), result.getFirstName());
        assertEquals(updateRequestDto.getLastName(), result.getLastName());

        verify(customerRepository, times(1)).findById(alice.getId());
        verify(customerRepository, times(1)).save(alice);
        verify(customerMapper, times(1)).toUpdateResponseDto(alice);
    }

    @Test
    @DisplayName("Register new customer - Success")
    void register_NewCustomer_Success() {
        CustomerRegistrationRequestDto requestDto = new CustomerRegistrationRequestDto();
        requestDto.setEmail(NEW_EMAIL);
        requestDto.setPassword("password");

        Role customerRole = new Role();
        customerRole.setId(1L);
        customerRole.setRole(Role.RoleName.ROLE_CUSTOMER);
        when(roleRepository.findByRole(Role.RoleName.ROLE_CUSTOMER)).thenReturn(customerRole);

        when(customerRepository.findByEmail(NEW_EMAIL)).thenReturn(Optional.empty());
        when(customerMapper.toModel(requestDto)).thenReturn(new Customer());
        when(passwordEncoder.encode(requestDto.getPassword())).thenReturn("hashedPassword");
        when(customerRepository.save(any())).thenReturn(new Customer());

        CustomerRegistrationResponseDto result = customerService.register(requestDto);

        assertNotNull(result);
        verify(customerRepository, times(1)).findByEmail(NEW_EMAIL);
        verify(roleRepository, times(1)).findByRole(Role.RoleName.ROLE_CUSTOMER);
        verify(customerMapper, times(1)).toModel(requestDto);
        verify(passwordEncoder, times(1)).encode(requestDto.getPassword());
        verify(customerRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Register new customer - Email already registered")
    void register_ExistingEmail_ThrowsRegistrationException() {
        CustomerRegistrationRequestDto requestDto = new CustomerRegistrationRequestDto();
        requestDto.setEmail(EXISTING_EMAIL);
        requestDto.setPassword("password");

        when(customerRepository.findByEmail(EXISTING_EMAIL))
                .thenReturn(Optional.of(new Customer()));

        RegistrationException exception = assertThrows(
                RegistrationException.class,
                () -> customerService.register(requestDto)
        );

        assertEquals(CUSTOMER_ALREADY_REGISTERED_MESSAGE, exception.getMessage());

        verify(customerRepository, times(1)).findByEmail(EXISTING_EMAIL);
        verify(roleRepository, never()).findByRole(any());
        verify(customerMapper, never()).toModel(any());
        verify(passwordEncoder, never()).encode(any());
        verify(customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Update customer roles - Success")
    void updateRole_Success() {
        Set<Long> oldIds = new HashSet<>(Arrays.asList(1L, 3L));
        CustomerUpdateRoleRequestDto updateDto = new CustomerUpdateRoleRequestDto(oldIds);
        Set<Long> roleIds = new HashSet<>();
        roleIds.add(1L);
        roleIds.add(2L);
        updateDto.setRoleIds(roleIds);

        Customer mockedCustomer = new Customer();
        mockedCustomer.setId(1L);
        mockedCustomer.setEmail("some@test.com");
        mockedCustomer.setFirstName("Nat");
        mockedCustomer.setLastName("Pops");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(mockedCustomer));
        when(customerRepository.save(mockedCustomer)).thenReturn(mockedCustomer);

        Role role1 = new Role();
        role1.setId(1L);
        Role role2 = new Role();
        role2.setId(2L);
        when(roleRepository.findAllById(roleIds)).thenReturn(List.of(role1, role2));

        CustomerResponseDtoWithRoles result = customerService.updateRole(
                mockedCustomer.getId(), updateDto);

        assertThat(result.getRoles())
                .extracting(Role::getId)
                .containsExactlyInAnyOrderElementsOf(roleIds);
        verify(customerRepository, times(1)).findById(anyLong());
        verify(roleRepository, times(1)).findAllById(roleIds);
        verify(customerRepository, times(1)).save(any());
        verify(customerMapper, times(1)).toDtoWithRoles(any());
    }

    @Test
    @DisplayName("Update customer roles - Customer not found")
    void updateRole_NotExistingId_EntityNotFoundExceptionExpected() {
        Long nonExistingCustomerId = 99L;
        Set<Long> roleIds = new HashSet<>(Arrays.asList(1L, 2L));
        Set<Long> oldIds = new HashSet<>(Arrays.asList(2L, 3L));
        CustomerUpdateRoleRequestDto updateRequestDto = new CustomerUpdateRoleRequestDto(oldIds);
        updateRequestDto.setRoleIds(roleIds);

        when(customerRepository.findById(nonExistingCustomerId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> customerService.updateRole(nonExistingCustomerId, updateRequestDto)
        );

        assertEquals(CUSTOMER_NOT_FOUND_MESSAGE + nonExistingCustomerId, exception.getMessage());

        verify(customerRepository, times(1)).findById(nonExistingCustomerId);
        verify(roleRepository, never()).findAllById(any());
        verify(customerRepository, never()).save(any());
    }
}
