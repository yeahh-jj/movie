package com.yeahhjj.movie.controller.api;

import com.yeahhjj.movie.domain.Reservation;
import com.yeahhjj.movie.dto.reservation.ReservationRequestDto;
import com.yeahhjj.movie.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationApiController {

    private ReservationService reservationService;

    @PostMapping("/reserve")
    public ResponseEntity<?> reserve(@RequestBody ReservationRequestDto reservationRequestDto, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new IllegalArgumentException("로그인 후 예약할 수 있습니다.");
        }

        Reservation reservation = reservationService.reserve(
                userId,
                reservationRequestDto.getScreeningId(),
                reservationRequestDto.getNumSeats()
        );

        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<?> cancel(@PathVariable Long reservationid, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if(userId == null) {
            throw new IllegalArgumentException("로그인을 해야 합니다.");
        }

        reservationService.cancelReservation(reservationid);

        return ResponseEntity.ok("예약이 취소되었습니다.");
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<?> getReservation(@PathVariable Long reservationId) {

        Reservation reservation = reservationService.getReservation(reservationId);

        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyReservations(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new IllegalArgumentException("LOGIN_REQUIRED");
        }

        List<Reservation> reservations = reservationService.getReservations(userId);

        return ResponseEntity.ok(reservations);
    }

}
