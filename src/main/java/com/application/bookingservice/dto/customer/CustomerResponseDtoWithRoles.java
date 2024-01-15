package com.application.bookingservice.dto.customer;

import com.application.bookingservice.model.Role;
import java.util.Set;
import lombok.Data;

@Data
public class CustomerResponseDtoWithRoles {
    private String email;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
}
