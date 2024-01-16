package com.application.bookingservice.service.address;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.application.bookingservice.dto.address.AddressRequestDto;
import com.application.bookingservice.model.Address;
import com.application.bookingservice.repository.address.AddressRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {
    private static Address address;
    private static Address updatedAddress;
    private static AddressRequestDto requestDto;
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private AddressServiceImpl addressService;

    @BeforeAll
    static void beforeAll() {
        address = new Address()
                .setId(1L)
                .setCountry("USA")
                .setCity("LA")
                .setStreet("North Croft Avenue")
                .setBuilding(15);

        updatedAddress = new Address()
                .setId(1L)
                .setCountry("Ukraine")
                .setCity("Lviv")
                .setStreet("Bohdan Khmelnytsky")
                .setBuilding(100);

        requestDto = new AddressRequestDto()
                .setCountry("Ukraine")
                .setCity("Lviv")
                .setStreet("Bohdan Khmelnytsky")
                .setBuilding(100);
    }

    @Test
    void updateById_ValidId_ReturnAddress() {
        Long id = 1L;

        Mockito.when(addressRepository.findById(id)).thenReturn(Optional.of(address));
        Mockito.when(addressRepository.save(address)).thenReturn(updatedAddress);

        Address expected = updatedAddress;
        Address actual = addressService.updateById(id, requestDto);

        assertEquals(expected, actual);
    }
}
