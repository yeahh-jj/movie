package com.yeahhjj.movie.domain;

import lombok.Data;

@Data
public class Movie {
    private Long id;
    private String title;
    private String director;
    private Genre genre;
    private String description;
    private int runningTime;
}
