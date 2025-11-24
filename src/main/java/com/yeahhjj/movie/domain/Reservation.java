package com.yeahhjj.movie.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Reservation {
    private Long id;
    private Long userId;
    private Long screeningId;
    private int numSeats;
    private LocalDateTime reservedAt;
}
