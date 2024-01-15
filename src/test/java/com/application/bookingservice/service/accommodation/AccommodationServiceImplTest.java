package com.application.bookingservice.service.accommodation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.application.bookingservice.dto.accommodation.AccommodationResponseDto;
import com.application.bookingservice.dto.address.AddressResponseDto;
import com.application.bookingservice.mapper.AccommodationMapper;
import com.application.bookingservice.model.Accommodation;
import com.application.bookingservice.model.Address;
import com.application.bookingservice.repository.accommodation.AccommodationRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceImplTest {
    private static Address address;
    private static Accommodation accommodation;
    private static AddressResponseDto addressResponseDto;
    private static AccommodationResponseDto accommodationResponseDto;
    @Mock
    private AccommodationRepository accommodationRepository;
    @Mock
    private AccommodationMapper accommodationMapper;

    @InjectMocks
    private AccommodationServiceImpl accommodationService;

    @BeforeAll
    static void beforeAll() {
        address = new Address()
                .setId(1L)
                .setCountry("Ukraine")
                .setCity("Lviv")
                .setStreet("Bohdan Khmelnytsky")
                .setBuilding(100);

        accommodation = new Accommodation()
                .setId(1L)
                .setType(Accommodation.Type.HOUSE)
                .setAddress(address)
                .setSize("100 sq.m.")
                .setAmenities("All")
                .setPrice(BigDecimal.valueOf(100))
                .setAvailableUnits(1);

        addressResponseDto = new AddressResponseDto()
                .setId(address.getId())
                .setCountry(address.getCountry())
                .setCity(address.getCity())
                .setStreet(address.getStreet())
                .setBuilding(address.getBuilding());

        accommodationResponseDto = new AccommodationResponseDto()
                .setId(accommodation.getId())
                .setType(accommodation.getType())
                .setAddress(addressResponseDto)
                .setSize(accommodation.getSize())
                .setAmenities(accommodation.getAmenities())
                .setPrice(accommodation.getPrice())
                .setAvailableUnits(accommodation.getAvailableUnits());
    }

    @Test
    void getAll_ValidPageable_ReturnAllAccommodations() {
        Pageable pageable = PageRequest.of(0,10);
        List<Accommodation> accommodations = List.of(accommodation);
        Page<Accommodation> accommodationPage =
                new PageImpl<>(accommodations, pageable, accommodations.size());

        List<AccommodationResponseDto> expected = List.of(accommodationResponseDto);

        Mockito.when(accommodationRepository.getAll(pageable)).thenReturn(accommodationPage);
        Mockito.when(accommodationMapper.toDto(accommodation)).thenReturn(accommodationResponseDto);

        List<AccommodationResponseDto> actual = accommodationService.getAll(pageable);
        assertEquals(expected, actual);
    }

    @Test
    void findById_ValidAccommodation_ReturnAccommodationResponseDto() {
        Long id = 1L;

        Mockito.when(accommodationRepository.findById(id))
                .thenReturn(Optional.ofNullable(accommodation));
        Mockito.when(accommodationMapper.toDto(accommodation))
                .thenReturn(accommodationResponseDto);

        AccommodationResponseDto expected = accommodationResponseDto;
        AccommodationResponseDto actual = accommodationService.findById(id);

        assertEquals(expected, actual);
    }
}
