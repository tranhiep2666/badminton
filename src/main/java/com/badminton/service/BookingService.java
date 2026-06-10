package com.badminton.service;

import com.badminton.dto.request.BookingRequest;
import com.badminton.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    List<BookingResponse> getBookingHistory(
            Long userId
    );
}