package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
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
        timeProvider = mock(TimeProvider.class);
        roomRepository = mock(RoomRepository.class);
        notificationService = mock(NotificationService.class);
        bookingSystem = new BookingSystem(timeProvider, roomRepository, notificationService);
    }

    @ParameterizedTest
    @CsvSource({" , 2024-12-12T10:00, 2024-12-12T11:00", "room1, , 2024-12-12T11:00", "room1, 2024-12-12T10:00, "})
    void shouldThrowExceptionWhenBookingWithNullValues(String roomId, LocalDateTime startTime, LocalDateTime endTime) {
        assertThatThrownBy(() -> bookingSystem.bookRoom(roomId, startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bokning kräver giltiga start- och sluttider samt rum-id");
    }

    @Test
    void shouldThrowExceptionWhenBookingInPast() {
        LocalDateTime pastTime = LocalDateTime.now().minusDays(1);
        when(timeProvider.getCurrentTime()).thenReturn(LocalDateTime.now());

        assertThatThrownBy(() -> bookingSystem.bookRoom("room1", pastTime, pastTime.plusHours(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Kan inte boka tid i dåtid");
    }

    @Test
    void shouldThrowExceptionIfBookingIsInTheFuture() {
         LocalDateTime startTime = LocalDateTime.now();
         LocalDateTime endTime = startTime.minusHours(1);

        when(timeProvider.getCurrentTime()).thenReturn(startTime);

        assertThatThrownBy(() -> bookingSystem.bookRoom("room1", startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Sluttid måste vara efter starttid");
    }





}
