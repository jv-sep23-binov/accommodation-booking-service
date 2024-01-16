package com.application.bookingservice.controller;

import com.application.bookingservice.dto.accommodation.AccommodationRequestDto;
import com.application.bookingservice.dto.accommodation.AccommodationResponseDto;
import com.application.bookingservice.dto.accommodation.AccommodationUpdateRequestDto;
import com.application.bookingservice.dto.address.AddressRequestDto;
import com.application.bookingservice.service.accommodation.AccommodationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accommodations")
@Tag(name = "Accommodation management.",
        description = "Endpoints for managing accommodations.")
public class AccommodationController {
    private final AccommodationService accommodationService;

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Save new accommodation.",
            description = "Permits the addition of new accommodations.")
    public AccommodationResponseDto save(
            @RequestBody @Valid AccommodationRequestDto accommodationRequestDto) {
        return accommodationService.save(accommodationRequestDto);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping
    @Operation(summary = "Get all accommodations.",
            description = "Provides a list of available accommodations.")
    public List<AccommodationResponseDto> getAll(Pageable pageable) {
        return accommodationService.getAll(pageable);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get accommodation by id.",
            description = "Retrieves detailed information about a specific accommodation.")
    public AccommodationResponseDto findById(@PathVariable Long id) {
        return accommodationService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PutMapping("/{id}")
    @Operation(summary = "Update accommodation.",
            description = "Allows updates to accommodation details.")
    public AccommodationResponseDto updateDetailsById(@PathVariable Long id,
                                                      @RequestBody @Valid
                                               AccommodationUpdateRequestDto requestDto) {
        return accommodationService.updateDetailsById(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PutMapping("/{id}/address")
    @Operation(summary = "Update accommodation address.",
            description = "Allows updates to accommodation address.")
    public AccommodationResponseDto updateAddressById(@PathVariable Long id,
                                                      @RequestBody @Valid
                                               AddressRequestDto requestDto) {
        return accommodationService.updateAddressById(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete accommodation.",
            description = "Enables the removal of accommodations.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        accommodationService.deleteById(id);
    }
}
