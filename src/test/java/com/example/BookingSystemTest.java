package com.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BookingSystemTest {
    private TimeProvider timeProvider;
    private RoomRepository roomRepository;
    private NotificationService notificationService;
    private BookingSystem bookingSystem;

    @BeforeEach
    void setUp() {
        timeProvider = Mockito.mock(TimeProvider.class);
        roomRepository = Mockito.mock(RoomRepository.class);
        notificationService = Mockito.mock(NotificationService.class);
        bookingSystem = new BookingSystem(timeProvider, roomRepository, notificationService);
    }
    @Test
    void shouldThrowExceptionWhenBookingWithNullValues() {
        assertThatThrownBy(() -> bookingSystem.bookRoom(null, null, null));
    }

}
