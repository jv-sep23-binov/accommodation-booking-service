package com.application.bookingservice.mapper;

import com.application.bookingservice.config.MapperConfig;
import com.application.bookingservice.dto.booking.BookingRequestDto;
import com.application.bookingservice.dto.booking.BookingResponseDto;
import com.application.bookingservice.dto.booking.BookingUpdateRequestDto;
import com.application.bookingservice.model.Booking;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookingMapper {
    @Named("toDto")
    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "accommodationId", source = "accommodation.id")
    BookingResponseDto toDto(Booking booking);

    @IterableMapping(qualifiedByName = "toDto")
    List<BookingResponseDto> toDtos(List<Booking> bookings);

    @Mapping(target = "accommodation.id", source = "accommodationId")
    Booking toEntity(BookingRequestDto requestDto);

    Booking toEntity(BookingUpdateRequestDto requestDto);
}
