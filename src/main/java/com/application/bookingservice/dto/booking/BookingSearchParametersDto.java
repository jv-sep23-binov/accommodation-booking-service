package com.application.bookingservice.dto.booking;

public record BookingSearchParametersDto(String[] customerIds, String[] statuses) {
}
