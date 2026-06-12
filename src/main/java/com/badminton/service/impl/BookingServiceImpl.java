package com.badminton.service.impl;

import com.badminton.dto.request.BookingRequest;
import com.badminton.dto.response.BookingResponse;
import com.badminton.entity.*;
import com.badminton.enums.BookingStatus;
import com.badminton.exception.ConflictException;
import com.badminton.exception.ResourceNotFoundException;
import com.badminton.repository.*;
import com.badminton.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final CourtRepository courtRepository;

    @Override
    public BookingResponse createBooking(BookingRequest request){

        User customer = userRepository
                .findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        Court court = courtRepository
                .findById(request.getCourtId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Court not found"));
        boolean existed = bookingRepository
                        .existsByCourtIdAndBookingDateAndStatusIn(
                                request.getCourtId(),
                                request.getBookingDate(),
                                List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
                        );

        if(existed){
            throw new ConflictException(
                    "Court already booked"
            );
        }

        Booking booking =
                Booking.builder()
                        .customer(customer)
                        .court(court)
                        .bookingDate(request.getBookingDate())
                        .startTime(request.getStartTime())
                        .endTime(
                                request.getEndTime()
                        )
                        .status(
                                BookingStatus.PENDING
                        )
                        .totalPrice(
                                BigDecimal.valueOf(100000)
                        )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .build();

        bookingRepository.save(booking);

        return BookingResponse.builder()
                .id(booking.getId())
                .customerName(
                        customer.getFullName()
                )
                .courtName(
                        court.getCourtName()
                )
                .bookingDate(
                        booking.getBookingDate()
                )
                .startTime(
                        booking.getStartTime()
                )
                .endTime(
                        booking.getEndTime()
                )
                .status(
                        booking.getStatus()
                )
                .totalPrice(
                        booking.getTotalPrice()
                )
                .build();

    }

    @Override
    public List<BookingResponse>
    getBookingHistory(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        return bookingRepository
                .findByCustomerIdOrderByCreatedAtDesc(
                        userId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BookingResponse confirmBooking(
            Long bookingId
    ) {

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found"
                        ));

        if (booking.getStatus() != BookingStatus.PENDING) {

            throw new ConflictException(
                    "Only pending booking can be confirmed"
            );
        }

        booking.setStatus(
                BookingStatus.CONFIRMED
        );

        bookingRepository.save(booking);

        return mapToResponse(booking);
    }

    @Override
    public BookingResponse cancelBooking(
            Long bookingId
    ) {

        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found"
                        ));

        if (booking.getStatus() != BookingStatus.PENDING) {

            throw new ConflictException(
                    "Only pending booking can be canceled"
            );
        }

        booking.setStatus(
                BookingStatus.CANCELED
        );

        bookingRepository.save(booking);

        return mapToResponse(booking);
    }













    private BookingResponse mapToResponse(
            Booking booking
    ) {

        return BookingResponse.builder()
                .id(booking.getId())
                .customerName(
                        booking.getCustomer()
                                .getFullName()
                )
                .courtName(
                        booking.getCourt()
                                .getCourtName()
                )
                .bookingDate(
                        booking.getBookingDate()
                )
                .startTime(
                        booking.getStartTime()
                )
                .endTime(
                        booking.getEndTime()
                )
                .status(
                        booking.getStatus()
                )
                .totalPrice(
                        booking.getTotalPrice()
                )
                .build();
    }
}