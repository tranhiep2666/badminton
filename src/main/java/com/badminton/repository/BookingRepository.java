package com.badminton.repository;

import com.badminton.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import com.badminton.enums.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerIdOrderByCreatedAtDesc(
            Long userId
    );
    boolean existsByCourtIdAndBookingDateAndStatusIn(
            Long courtId,
            LocalDate bookingDate,
            List<BookingStatus> statuses
    );
}