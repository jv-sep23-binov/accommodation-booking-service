package com.application.bookingservice.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerUpdateRequestDto {
    @Email
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @Size(max = 50)
    @NotBlank(message = "First Name cannot be blank")
    private String firstName;
    @Size(max = 50)
    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;
}
