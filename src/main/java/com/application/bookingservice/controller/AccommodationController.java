package com.application.bookingservice.controller;

import com.application.bookingservice.service.accommodation.AccommodationService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accommodations")
@Tag(name = "Accommodation management.",
        description = "Endpoints for managing accommodations.")
public class AccommodationController {
    private AccommodationService accommodationService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Save new accommodation.",
            description = "Permits the addition of new accommodations.")
    public Object save(@RequestBody @Valid Object accommodationRequestDto) {
        return accommodationService.save(accommodationRequestDto);
    }

    @GetMapping
    @Operation(summary = "Get all accommodations.",
            description = "Provides a list of available accommodations.")
    public List<Object> getAll(Pageable pageable) {
        return accommodationService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get accommodation by id.",
            description = "Retrieves detailed information about a specific accommodation.")
    public Object findById(@PathVariable Long id) {
        return accommodationService.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update accommodation.",
            description = "Allows updates to accommodation details.")
    public Object updateById(@PathVariable Long id,
                             @RequestBody @Valid Object accommodationRequestDto) {
        return accommodationService.updateById(id, accommodationRequestDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete accommodation.",
            description = "Enables the removal of accommodations.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        accommodationService.deleteById(id);
    }

}
