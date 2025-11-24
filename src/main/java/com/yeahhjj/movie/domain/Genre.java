package com.yeahhjj.movie.domain;

public enum Genre {
    ACTION("액션"),
    COMEDY("코미디"),
    DRAMA("드라마"),
    HORROR("공포"),
    ROMANCE("로맨스"),
    SF("SF"),
    THRILLER("스릴러"),
    FANTASY("판타지"),
    ANIMATION("애니메이션"),
    CRIME("범죄"),
    MYSTERY("미스터리"),
    ADVENTURE("모험");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
