package com.yeahhjj.movie.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Screening {
    private Long id;
    private Long movieId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int totalSeats;
    private int availableSeats;
    private String theater;

    public Screening(Long movieId, LocalDateTime startTime, LocalDateTime endTime, int totalSeats, String theater) {
        this.movieId = movieId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.theater = theater;
    }

    public boolean reserveSeats(int numSeats) {
        if(numSeats <= 0 || availableSeats < numSeats) return false;
        availableSeats -= numSeats;
        return true;
    }
}
