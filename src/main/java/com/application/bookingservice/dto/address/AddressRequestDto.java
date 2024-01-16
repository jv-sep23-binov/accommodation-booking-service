package com.application.bookingservice.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddressRequestDto {
    @NotBlank
    private String city;
    @NotBlank
    private String country;
    @NotBlank
    private String street;
    @NotNull
    @Positive
    private Integer building;
    @Positive
    private Integer flat;
}
