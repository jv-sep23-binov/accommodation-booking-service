package com.application.bookingservice.dto.accommodation;

import com.application.bookingservice.model.Accommodation.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccommodationUpdateRequestDto {
    @NotNull
    private Type type;
    @NotBlank
    private String size;
    @NotBlank
    private String amenities;
    @Positive
    @NotNull
    private BigDecimal price;
    @Positive
    @NotNull
    private Integer availableUnits;
}
