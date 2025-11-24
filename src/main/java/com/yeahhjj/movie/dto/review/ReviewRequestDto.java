package com.yeahhjj.movie.dto.review;

import lombok.Data;

@Data
public class ReviewRequestDto {
    private Long reservationId;
    private Double rating;
    private String content;
}
