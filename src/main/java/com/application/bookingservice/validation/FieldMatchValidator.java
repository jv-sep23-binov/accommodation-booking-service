package com.application.bookingservice.validation;

import com.application.bookingservice.dto.customer.CustomerRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator
        implements ConstraintValidator<FieldMatch, CustomerRegistrationRequestDto> {
    @Override
    public boolean isValid(
            CustomerRegistrationRequestDto requestDto,
            ConstraintValidatorContext constraintValidatorContext
    ) {
        return requestDto.getPassword().equals(requestDto.getRepeatPassword());
    }
}
