package com.application.bookingservice.service.address;

import com.application.bookingservice.dto.address.AddressRequestDto;
import com.application.bookingservice.model.Address;

public interface AddressService {
    Address updateById(Long id, AddressRequestDto requestDto);
}
