package com.application.bookingservice.service.address;

import com.application.bookingservice.dto.address.AddressRequestDto;
import com.application.bookingservice.exception.EntityNotFoundException;
import com.application.bookingservice.model.Address;
import com.application.bookingservice.repository.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private static final String EXCEPTION_MSG_CANNOT_FIND = "Can't find address with id: ";
    private final AddressRepository addressRepository;

    @Override
    public Address updateById(Long id, AddressRequestDto requestDto) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(EXCEPTION_MSG_CANNOT_FIND + id))
                .setCity(requestDto.getCity())
                .setCountry(requestDto.getCountry())
                .setStreet(requestDto.getStreet())
                .setBuilding(requestDto.getBuilding())
                .setFlat(requestDto.getFlat());
        return addressRepository.save(address);
    }
}
