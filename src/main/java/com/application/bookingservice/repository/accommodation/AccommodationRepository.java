package com.application.bookingservice.repository.accommodation;

import com.application.bookingservice.model.Accommodation;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    @Query("select a from Accommodation a join fetch a.address")
    List<Accommodation> getAll(Pageable pageable);

    @Query("select a from Accommodation a join fetch a.address where a.id = :id")
    Optional<Accommodation> findById(Long id);
}
