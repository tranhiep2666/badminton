package com.badminton.dto.response;

import com.badminton.enums.BookingStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class BookingResponse {

    private Long id;

    private String customerName;

    private String courtName;

    private LocalDate bookingDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private BookingStatus status;

    private BigDecimal totalPrice;
}