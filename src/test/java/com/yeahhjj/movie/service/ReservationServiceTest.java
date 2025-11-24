package com.yeahhjj.movie.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.yeahhjj.movie.domain.Reservation;
import com.yeahhjj.movie.domain.Screening;
import com.yeahhjj.movie.repository.ReservationRepository;
import com.yeahhjj.movie.repository.ScreeningRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ScreeningRepository screeningRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void 영화_예매() {
        Long userId = 1L;
        Long screeningId = 2L;
        int count = 3;

        Screening screening = new Screening(
                1L,
                LocalDateTime.of(2025, 1, 1, 14, 0),
                LocalDateTime.of(2025, 1, 1, 16, 30),
                16,
                "1관"
        );
        screening.setId(screeningId);

        given(screeningRepository.findById(screeningId)).willReturn(screening);

        reservationService.reserve(userId, screeningId, count);

        verify(reservationRepository, times(1))
                .save(any(Reservation.class));

        verify(screeningRepository, times(1))
                .updateAvailableSeats(eq(screeningId), eq(13));
    }

    @Test
    void 영화_예매_실패() {
        Long userId = 1L;
        Long screeningId = 2L;
        int count = 3;

        Screening screening = new Screening(
                1L,
                LocalDateTime.of(2025, 1, 1, 14, 0),
                LocalDateTime.of(2025, 1, 1, 16, 30),
                2,
                "1관"
        );
        screening.setId(screeningId);

        given(screeningRepository.findById(screeningId)).willReturn(screening);

        assertThatThrownBy(() -> reservationService.reserve(userId, screeningId, count))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잔여 좌석이 부족합니다.");

        verify(reservationRepository, never()).save(any());
        verify(screeningRepository, never()).updateAvailableSeats(anyLong(), anyInt());
    }

    @Test
    void 예매_상세_조회() {
        Long reservationId = 1L;

        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setUserId(2L);
        reservation.setScreeningId(3L);
        reservation.setNumSeats(4);
        reservation.setReservedAt(LocalDateTime.now());

        given(reservationRepository.findById(reservationId)).willReturn(reservation);

        assertThat(reservationService.getReservation(reservationId).getId())
                .isEqualTo(reservationId);
    }

    @Test
    void 예매_상세_조회_실패() {
        Long reservationId = 1L;

        given(reservationRepository.findById(reservationId)).willReturn(null);

        assertThatThrownBy(() -> reservationService.getReservation(reservationId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예매 정보를 찾을 수 없습니다.");
    }

    @Test
    void 예매_목록_조회() {
        Long userId = 1L;

        Reservation r1 = new Reservation();
        r1.setId(1L);
        r1.setUserId(userId);
        r1.setScreeningId(2L);
        r1.setNumSeats(4);
        r1.setReservedAt(LocalDateTime.now());

        Reservation r2 = new Reservation();
        r2.setId(3L);
        r2.setUserId(userId);
        r2.setScreeningId(2L);
        r2.setNumSeats(4);
        r2.setReservedAt(LocalDateTime.now().plusMinutes(15));

        List<Reservation> reservations = Arrays.asList(r1, r2);

        given(reservationRepository.findByUserId(userId)).willReturn(reservations);

        List<Reservation> result = reservationService.getReservations(userId);

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(3L);
    }

    @Test
    void 예매_목록_조회_실패() {
        Long userId = 1L;

        given(reservationRepository.findByUserId(userId)).willReturn(Collections.emptyList());

        List<Reservation> result = reservationService.getReservations(userId);

        assertThat(result).isEmpty();
    }

    @Test
    void 예매_취소() {
        Long reservationId = 1L;

        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setUserId(2L);
        reservation.setScreeningId(3L);
        reservation.setNumSeats(4);
        reservation.setReservedAt(LocalDateTime.now());

        Screening screening = new Screening(
                1L,
                LocalDateTime.of(2025, 1, 1, 14, 0),
                LocalDateTime.of(2025, 1, 1, 16, 30),
                16,
                "1관"
        );
        screening.setId(3L);
        screening.setAvailableSeats(12);

        given(reservationRepository.findById(reservationId)).willReturn(reservation);
        given(screeningRepository.findById(3L)).willReturn(screening);

        reservationService.cancelReservation(reservationId);

        verify(reservationRepository, times(1)).delete(reservationId);
        verify(screeningRepository, times(1))
                .updateAvailableSeats(3L, 16);
    }

    @Test
    void 예매_취소_실패() {
        Long reservationId = 1L;

        given(reservationRepository.findById(reservationId)).willReturn(null);

        assertThatThrownBy(() -> reservationService.cancelReservation(reservationId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예매 정보를 찾을 수 없습니다.");

        verify(reservationRepository, never()).delete(anyLong());
        verify(screeningRepository, never()).updateAvailableSeats(anyLong(), anyInt());
    }
}
