package com.yeahhjj.movie.dto.screening;

import lombok.Data;

@Data
public class ScreeningRequestDto {
    private Long movieId;
    private String startTime;
    private String theater;
}