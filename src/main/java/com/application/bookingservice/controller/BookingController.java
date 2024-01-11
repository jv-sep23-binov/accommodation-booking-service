package com.application.bookingservice.controller;

import com.application.bookingservice.dto.booking.BookingRequestDto;
import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.dto.booking.BookingUpdateRequestDto;
import com.application.bookingservice.model.Customer;
import com.application.bookingservice.service.booking.BookingService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
@Tag(name = "Booking management.",
        description = "Endpoints for managing bookings.")
public class BookingController {
    private BookingService bookingService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new booking.",
            description = "Permits the creation of new accommodation bookings.")
    public BookingResponseDto create(@RequestBody @Valid BookingRequestDto requestBookingDto,
                                     Authentication authentication) {
        Customer customer = (Customer) authentication.getPrincipal();
        return bookingService.save(customer.getId(), requestBookingDto);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/my")
    @Operation(summary = "Get all customer bookings",
            description = "Retrieves customer bookings.")
    public List<BookingResponseDto> getAll(Pageable pageable, Authentication authentication) {
        Customer customer = (Customer) authentication.getPrincipal();
        return bookingService.getAll(customer.getId(), pageable);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get booking by id.",
            description = "Provides information about a specific booking.")
    public BookingResponseDto findById(@PathVariable Long id) {
        return bookingService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PutMapping("{id}")
    @Operation(summary = "Update booking by id.",
            description = "Allows customers to update their booking details.")
    public BookingResponseDto update(@PathVariable Long id,
                                     @RequestBody @Valid BookingUpdateRequestDto requestDto) {
        return bookingService.updateById(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete booking by id.",
            description = "Enables the cancellation of bookings.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        bookingService.deleteById(id);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping
    @Operation(summary = "Get bookings by customer and status.",
            description = "Retrieves bookings based on customer ID and their status.")
    public List<BookingResponseDto> getByUserAndStatus(
            @RequestParam Long userId,
            @RequestParam String status,
            Pageable pageable) {
        return bookingService.findByUserIdAndStatus(userId, status, pageable);
    }
}
