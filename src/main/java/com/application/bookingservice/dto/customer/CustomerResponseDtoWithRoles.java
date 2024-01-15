package com.application.bookingservice.dto.customer;

import com.application.bookingservice.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Data;

@Data
public class CustomerResponseDtoWithRoles {
    @Email
    @NotBlank(message = "Name cannot be blank")
    private String email;
    @Size(max = 50)
    @NotBlank(message = "Name cannot be blank")
    private String firstName;
    @Size(max = 50)
    @NotBlank(message = "Name cannot be blank")
    private String lastName;
    private Set<Role> roles;
}
