package com.yeahhjj.movie.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Review {
    private Long id;
    private Long reservationId;
    private Long userId;
    private Long movieId;
    private Double rating;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
