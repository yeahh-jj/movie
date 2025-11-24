package com.yeahhjj.movie.dto.review;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private Long userId;
    private Long movieId;
    private Long reservationId;
    private Double rating;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}