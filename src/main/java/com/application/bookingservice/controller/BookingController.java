package com.application.bookingservice.controller;

import com.application.bookingservice.dto.booking.BookingRequestDto;
import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.dto.booking.BookingSearchParametersDto;
import com.application.bookingservice.dto.booking.BookingUpdateRequestDto;
import com.application.bookingservice.dto.booking.BookingUpdateStatusRequestDto;
import com.application.bookingservice.model.Customer;
import com.application.bookingservice.service.booking.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
@Tag(name = "Booking management.",
        description = "Endpoints for managing bookings.")
public class BookingController {
    private final BookingService bookingService;

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update booking status.",
            description = "Allows manager to change booking status.",
            security = @SecurityRequirement(name = "bearerAuth"))
    public BookingResponseDto updateStatus(
            @PathVariable Long id,
            @RequestBody @Valid BookingUpdateStatusRequestDto requestDto
    ) {
        return bookingService.updateStatus(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new booking.",
            description = "Permits the creation of new accommodation bookings.",
            security = @SecurityRequirement(name = "bearerAuth"))
    public BookingResponseDto create(@RequestBody @Valid BookingRequestDto requestBookingDto,
                                     Authentication authentication) {
        Customer customer = (Customer) authentication.getPrincipal();
        return bookingService.save(customer.getId(), requestBookingDto);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/my")
    @Operation(summary = "Get all customer bookings",
            description = "Retrieves customer bookings.",
            security = @SecurityRequirement(name = "bearerAuth"))
    public List<BookingResponseDto> getAll(Pageable pageable, Authentication authentication) {
        Customer customer = (Customer) authentication.getPrincipal();
        return bookingService.getAll(customer.getId(), pageable);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get booking by id.",
            description = "Provides information about a specific booking.",
            security = @SecurityRequirement(name = "bearerAuth"))
    public BookingResponseDto findById(Authentication authentication,
                                       @PathVariable Long id) {
        Customer customer = (Customer) authentication.getPrincipal();
        return bookingService.findById(customer.getId(), id);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PutMapping("{id}")
    @Operation(summary = "Update booking by id.",
            description = "Allows customers to update their booking details.",
            security = @SecurityRequirement(name = "bearerAuth"))
    public BookingResponseDto update(Authentication authentication,
                                     @PathVariable Long id,
                                     @RequestBody @Valid BookingUpdateRequestDto requestDto
    ) {
        Customer customer = (Customer) authentication.getPrincipal();
        return bookingService.updateById(customer.getId(), id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete booking by id.",
            description = "Enables the cancellation of bookings.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(Authentication authentication, @PathVariable Long id) {
        Customer customer = (Customer) authentication.getPrincipal();
        bookingService.deleteById(customer.getId(), id);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/search")
    @Operation(summary = "Get bookings by customer and status.",
            description = "Retrieves bookings based on customer ID and their status.",
            security = @SecurityRequirement(name = "bearerAuth"))
    public List<BookingResponseDto> search(
            BookingSearchParametersDto searchParameters
    ) {
        return bookingService.search(searchParameters);
    }
}
