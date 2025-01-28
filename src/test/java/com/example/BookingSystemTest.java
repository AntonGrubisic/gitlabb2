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
        timeProvider = Mockito.mock(TimeProvider.class);
        roomRepository = Mockito.mock(RoomRepository.class);
        notificationService = Mockito.mock(NotificationService.class);
        bookingSystem = new BookingSystem(timeProvider, roomRepository, notificationService);
    }

    @Test
    void testToBookRoom(){


    }
    @Test
    void shouldThrowExceptionWhenRoomIdIsNull() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingSystem.bookRoom(null, startTime, endTime);
        });

        assertEquals("Bokning kräver giltiga start- och sluttider samt rum-id", exception.getMessage());
    }



    @Test
    void shouldThrowExceptionWhenBookingInPast() {
        LocalDateTime pastTime = LocalDateTime.now().minusDays(1);
        when(timeProvider.getCurrentTime()).thenReturn(pastTime);

        assertThatThrownBy(() -> bookingSystem.bookRoom("room1", pastTime, pastTime.plusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Rummet existerar inte");
    }
    @Test
    void shouldThrowExceptionWhenEndTimeBeforeStartTime() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        LocalDateTime endTime = startTime.minusHours(1);

        assertThatThrownBy(() -> bookingSystem.bookRoom("room1", startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Sluttid måste vara efter starttid");
    }

}
