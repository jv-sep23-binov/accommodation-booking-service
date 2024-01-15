package com.application.bookingservice.dto.accommodation;

import com.application.bookingservice.dto.address.AddressResponseDto;
import com.application.bookingservice.model.Accommodation.Type;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccommodationResponseDto {
    private Long id;
    private Type type;
    private AddressResponseDto address;
    private String size;
    private String amenities;
    private BigDecimal price;
    private Integer availableUnits;
}
