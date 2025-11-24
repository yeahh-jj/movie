package com.yeahhjj.movie.dto.reservation;

import lombok.Data;

@Data
public class ReservationRequestDto {
    private Long screeningId;
    private int numSeats;
}
