package com.application.bookingservice.dto.address;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddressResponseDto {
    private Long id;
    private String city;
    private String country;
    private String street;
    private Integer building;
    private Integer flat;
}
