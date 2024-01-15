package com.application.bookingservice.dto.customer;

import lombok.Data;

@Data
public class CustomerUpdateResponseDto {
    private String email;
    private String firstName;
    private String lastName;
}
