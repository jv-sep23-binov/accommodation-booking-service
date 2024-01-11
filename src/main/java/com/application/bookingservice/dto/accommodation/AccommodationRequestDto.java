package com.application.bookingservice.dto.accommodation;

import com.application.bookingservice.model.Accommodation.Type;
import com.application.bookingservice.model.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccommodationRequestDto {
    @NotBlank
    private Type type;
    @NotBlank
    private Address address;
    @NotBlank
    private String size;
    @NotBlank
    private String amenities;
    @Positive
    @NotBlank
    private BigDecimal price;
    @Positive
    @NotBlank
    private Integer availableUnits;
}
