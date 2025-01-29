package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BookingSystemTest {
    private TimeProvider timeProvider;
    private RoomRepository roomRepository;
    private NotificationService notificationService;
    private BookingSystem bookingSystem;
    private LocalDateTime now;
    private Room testRoom;

    @BeforeEach
    void setUp() {
        timeProvider = mock(TimeProvider.class);
        roomRepository = mock(RoomRepository.class);
        notificationService = mock(NotificationService.class);
        bookingSystem = new BookingSystem(timeProvider, roomRepository, notificationService);
        now = LocalDateTime.now();

        Room testRoom = mock(Room.class);
        when(roomRepository.findById("room1")).thenReturn(java.util.Optional.of(testRoom));
        when(testRoom.isAvailable(any(), any())).thenReturn(true);

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
    @Test
    void shouldThrowExceptionIfRoomDoesNotExist() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);

        when(timeProvider.getCurrentTime()).thenReturn(startTime);

        assertThatThrownBy(() -> bookingSystem.bookRoom("nonExistentRoom", startTime, endTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Rummet existerar inte");
    }
    @Test
    void shouldReturnFalseIfRoomIsNotAvailable() {
        LocalDateTime startTime = now.plusDays(1);
        LocalDateTime endTime = startTime.plusHours(2);

        when(timeProvider.getCurrentTime()).thenReturn(now);

        Room testRoom = mock(Room.class);
        when(roomRepository.findById("room1")).thenReturn(Optional.of(testRoom));
        when(testRoom.isAvailable(startTime, endTime)).thenReturn(false);

        boolean result = bookingSystem.bookRoom("room1", startTime, endTime);
        assertFalse(result);
    }
    @Test
    void shouldNotFailIfNotificationFails() throws NotificationException {
        LocalDateTime startTime = now.plusDays(1);
        LocalDateTime endTime = startTime.plusHours(2);

        when(timeProvider.getCurrentTime()).thenReturn(now);

        Room testRoom = mock(Room.class);
        when(roomRepository.findById("room1")).thenReturn(Optional.of(testRoom));
        when(testRoom.isAvailable(startTime, endTime)).thenReturn(true);

        doThrow(new NotificationException("Notifiering misslyckades")).when(notificationService).sendBookingConfirmation(any());

        boolean result = bookingSystem.bookRoom("room1", startTime, endTime);
        assertTrue(result);
    }

    @Test
    void shouldThrowExceptionIfStartTimeOrEndTimeIsNull() {
        assertThatThrownBy(() -> bookingSystem.getAvailableRooms(null, now.plusHours(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Måste ange både start- och sluttid");

        assertThatThrownBy(() -> bookingSystem.getAvailableRooms(now, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Måste ange både start- och sluttid");
    }
    @Test
    void shouldThrowExceptionIfEndTimeIsBeforeStartTime() {
        assertThatThrownBy(() -> bookingSystem.getAvailableRooms(now.plusHours(2), now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Sluttid måste vara efter starttid");
    }

    @Test
    void shouldThrowExceptionIfBookingIdIsNull() {
        assertThatThrownBy(() -> bookingSystem.cancelBooking(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Boknings-id kan inte vara null");

    }
}
