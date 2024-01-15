package com.application.bookingservice.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerDto {
    @Email
    @NotNull(message = "can't be null")
    private String email;
    @NotNull(message = "can't be null")
    private String firstName;
    @NotNull(message = "can't be null")
    private String lastName;
}
