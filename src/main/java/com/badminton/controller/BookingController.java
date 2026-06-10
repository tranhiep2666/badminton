package com.badminton.controller;

import com.badminton.dto.request.BookingRequest;
import com.badminton.dto.response.ResponseDTO;
import com.badminton.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(
            @Valid
            @RequestBody BookingRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ResponseDTO.builder()
                                .success(true)
                                .message(
                                        "Booking created successfully"
                                )
                                .data(
                                        bookingService
                                                .createBooking(
                                                        request
                                                )
                                )
                                .build()
                );
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getHistory(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .success(true)
                        .message(
                                "Booking history retrieved"
                        )
                        .data(
                                bookingService
                                        .getBookingHistory(
                                                userId
                                        )
                        )
                        .build()
        );
    }
}