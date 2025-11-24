package com.yeahhjj.movie.service;

import com.yeahhjj.movie.domain.Reservation;
import com.yeahhjj.movie.domain.Screening;
import com.yeahhjj.movie.repository.ReservationRepository;
import com.yeahhjj.movie.repository.ScreeningRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScreeningRepository screeningRepository;

    public Reservation reserve(Long userId, Long screeningId, int numSeats) {
        Screening screening = screeningRepository.findById(screeningId);

        if (screening == null) {
            throw new IllegalArgumentException("상영 정보를 찾을 수 없습니다.");
        }

        boolean reserveSeatsSuccess = screening.reserveSeats(numSeats);
        if (!reserveSeatsSuccess) {
            throw new IllegalArgumentException("잔여 좌석이 부족합니다.");
        }

        Reservation reservation = new Reservation();
        reservation.setUserId(userId);
        reservation.setScreeningId(screeningId);
        reservation.setNumSeats(numSeats);
        reservation.setReservedAt(LocalDateTime.now());

        reservationRepository.save(reservation);

        screeningRepository.updateAvailableSeats(
                screeningId, screening.getAvailableSeats()
        );

        return reservation;
    }

    public Reservation getReservation(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("예매 정보를 찾을 수 없습니다.");
        }

        return reservation;
    }

    public List<Reservation> getReservations(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("예매 정보를 찾을 수 없습니다.");
        }

        Long screeningId = reservation.getScreeningId();
        Screening screening = screeningRepository.findById(screeningId);
        if (screening == null) {
            throw new IllegalArgumentException("상영 정보를 찾을 수 없습니다.");
        }

        int backupAvailableSeats = screening.getAvailableSeats() + reservation.getNumSeats();
        screeningRepository.updateAvailableSeats(screeningId, backupAvailableSeats);

        reservationRepository.delete(reservationId);
    }
}
