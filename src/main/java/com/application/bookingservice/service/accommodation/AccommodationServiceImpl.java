package com.application.bookingservice.service.accommodation;

import com.application.bookingservice.dto.accommodation.AccommodationRequestDto;
import com.application.bookingservice.dto.accommodation.AccommodationResponseDto;
import com.application.bookingservice.dto.accommodation.AccommodationUpdateRequestDto;
import com.application.bookingservice.dto.address.AddressRequestDto;
import com.application.bookingservice.exception.EntityNotFoundException;
import com.application.bookingservice.mapper.AccommodationMapper;
import com.application.bookingservice.model.Accommodation;
import com.application.bookingservice.model.Address;
import com.application.bookingservice.repository.accommodation.AccommodationRepository;
import com.application.bookingservice.repository.address.AddressRepository;
import com.application.bookingservice.service.address.AddressService;
import com.application.bookingservice.service.bot.NotificationService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {
    private static final String EXCEPTION_MSG_CANNOT_FIND = "Can't find accommodation with id: ";
    private final AccommodationRepository accommodationRepository;
    private final AccommodationMapper accommodationMapper;
    private final AddressService addressService;
    private final AddressRepository addressRepository;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public AccommodationResponseDto save(AccommodationRequestDto accommodationRequestDto) {
        Address savedAddress = addressRepository.save(accommodationRequestDto.getAddress());

        Accommodation accommodation = accommodationMapper.toEntity(accommodationRequestDto);
        accommodation.setAddress(savedAddress);

        AccommodationResponseDto savedAccommodationDto
                = accommodationMapper.toDto(accommodationRepository.save(accommodation));

        notificationService.accommodationCreatedMessage(savedAccommodationDto);
        return savedAccommodationDto;
    }

    @Override
    public List<AccommodationResponseDto> getAll(Pageable pageable) {
        return accommodationMapper.toDtos(accommodationRepository.getAll(pageable));
    }

    @Override
    public AccommodationResponseDto findById(Long id) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(EXCEPTION_MSG_CANNOT_FIND + id));
        return accommodationMapper.toDto(accommodation);
    }

    @Override
    @Transactional
    public AccommodationResponseDto updateDetailsById(Long id,
                                                      AccommodationUpdateRequestDto requestDto) {
        Accommodation accommodation = accommodationRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(EXCEPTION_MSG_CANNOT_FIND + id))
                .setType(requestDto.getType())
                .setSize(requestDto.getSize())
                .setAmenities(requestDto.getAmenities())
                .setPrice(requestDto.getPrice())
                .setAvailableUnits(requestDto.getAvailableUnits());
        AccommodationResponseDto updatedAccommodationDto = accommodationMapper
                .toDto(accommodationRepository.save(accommodation));
        notificationService.accommodationUpdateMessage(updatedAccommodationDto);
        return updatedAccommodationDto;
    }

    @Override
    public void deleteById(Long id) {
        accommodationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public AccommodationResponseDto updateAddressById(Long id, AddressRequestDto requestDto) {
        Accommodation accommodation = accommodationRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(EXCEPTION_MSG_CANNOT_FIND + id));
        Long addressId = accommodation.getAddress().getId();
        Address address = addressService.updateById(addressId, requestDto);
        accommodation.setAddress(address);
        AccommodationResponseDto updatedAccommodationDto = accommodationMapper
                .toDto(accommodationRepository.save(accommodation));
        notificationService.accommodationUpdateAddressMessage(updatedAccommodationDto);
        return updatedAccommodationDto;
    }
}
