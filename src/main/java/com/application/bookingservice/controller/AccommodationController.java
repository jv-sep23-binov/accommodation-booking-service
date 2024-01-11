package com.application.bookingservice.controller;

import com.application.bookingservice.service.AccommodationService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accommodations")
public class AccommodationController {
    private AccommodationService accommodationService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Object save(@RequestBody @Valid Object accommodationRequestDto) {
        return accommodationService.save(accommodationRequestDto);
    }

    @GetMapping
    public List<Object> getAll(Pageable pageable) {
        return accommodationService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Object findById(@PathVariable Long id) {
        return accommodationService.findById(id);
    }

    @PutMapping("/{id}")
    public Object updateById(@PathVariable Long id,
                             @RequestBody @Valid Object updateAccommodationRequestDto) {
        return accommodationService.updateById(id, updateAccommodationRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        accommodationService.deleteById(id);
    }

}
