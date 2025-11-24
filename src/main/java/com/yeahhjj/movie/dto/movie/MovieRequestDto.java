package com.yeahhjj.movie.dto.movie;

import lombok.Data;

@Data
public class MovieRequestDto {
    private String title;
    private String director;
    private String genre;
    private String description;
    private int runningTime;
}
