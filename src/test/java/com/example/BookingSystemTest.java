package com.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

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
        assertThatThrownBy(() -> bookingSystem.bookRoom(null, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bokning kräver giltiga start- och sluttider samt rum-id");
    }

    @Test
    void shouldThrowExceptionWhenBookingInPast() {
        LocalDateTime pastTime = LocalDateTime.now().minusDays(1);
        when(timeProvider.getCurrentTime()).thenReturn(pastTime);

        assertThatThrownBy(() -> bookingSystem.bookRoom("room1", pastTime, pastTime.plusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Rummet existerar inte");
    }

}
