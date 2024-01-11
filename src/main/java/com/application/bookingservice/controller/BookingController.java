package com.application.bookingservice.controller;

import com.application.bookingservice.service.booking.BookingService;
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
public class BookingController {
    private BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Object create(@RequestBody @Valid Object requestBookingDto) {
        //Customer customer = (Customer) authentication.getPrincipal();
        Long customerId = 1L;
        return bookingService.save(customerId, requestBookingDto);
    }

    @GetMapping("/my")
    public List<Object> getAll(Pageable pageable) {
        //Customer customer = (Customer) authentication.getPrincipal();
        Long customerId = 1L;
        return bookingService.getAll(customerId, pageable);
    }

    @GetMapping("/{id}")
    public Object findById(@PathVariable Long id) {
        return bookingService.findById(id);
    }

    @PutMapping("{id}")
    public Object update(@PathVariable Long id, @RequestBody @Valid Object bookingRequestDto) {
        return bookingService.updateById(id, bookingRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        bookingService.deleteById(id);
    }

    @GetMapping
    public List<Object> getByUserAndStatus(
            @RequestParam Long userId,
            @RequestParam Object status,
            Pageable pageable) {
        return bookingService.findByUserIdAndStatus(userId, status, pageable);
    }
}
