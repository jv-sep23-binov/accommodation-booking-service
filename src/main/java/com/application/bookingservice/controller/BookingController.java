package com.application.bookingservice.controller;

import com.application.bookingservice.service.booking.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
@Tag(name = "Booking management.", description = "Endpoints for managing bookings.")
public class BookingController {
    private BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new booking.", description = "Permits the creation of new accommodation bookings.")
    public Object create(@RequestBody @Valid Object requestBookingDto) {
        //Customer customer = (Customer) authentication.getPrincipal();
        Long customerId = 1L;
        return bookingService.save(customerId, requestBookingDto);
    }

    @GetMapping("/my")
    @Operation(summary = "Get all customer bookings", description = "Retrieves customer bookings.")
    public List<Object> getAll(Pageable pageable) {
        //Customer customer = (Customer) authentication.getPrincipal();
        Long customerId = 1L;
        return bookingService.getAll(customerId, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking by id.", description = "Provides information about a specific booking.")
    public Object findById(@PathVariable Long id) {
        return bookingService.findById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update booking by id.", description = "Allows customers to update their booking details.")
    public Object update(@PathVariable Long id, @RequestBody @Valid Object bookingRequestDto) {
        return bookingService.updateById(id, bookingRequestDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete booking by id.", description = "Enables the cancellation of bookings.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        bookingService.deleteById(id);
    }

    @GetMapping
    @Operation(summary = "Get bookings by customer and status.", description = "Retrieves bookings based on customer ID and their status.")
    public List<Object> getByUserAndStatus(
            @RequestParam Long userId,
            @RequestParam Object status,
            Pageable pageable) {
        return bookingService.findByUserIdAndStatus(userId, status, pageable);
    }
}
