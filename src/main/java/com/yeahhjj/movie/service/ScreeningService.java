package com.yeahhjj.movie.service;

import com.yeahhjj.movie.domain.Movie;
import com.yeahhjj.movie.domain.Screening;
import com.yeahhjj.movie.dto.screening.ScreeningRequestDto;
import com.yeahhjj.movie.repository.MovieRepository;
import com.yeahhjj.movie.repository.ScreeningRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;

    private static final int CLEANING_TIME = 15;
    private static final int DEFAULT_SEATS = 16;

    public Screening register(ScreeningRequestDto screeningRequestDto) {

        Movie movie = movieRepository.findById(screeningRequestDto.getMovieId());
        if (movie == null) {
            throw new IllegalArgumentException("영화를 찾을 수 없습니다.");
        }

        LocalDateTime startTime = LocalDateTime.parse(screeningRequestDto.getStartTime());
        LocalDateTime endTime = startTime
                .plusMinutes(movie.getRunningTime())
                .plusMinutes(CLEANING_TIME);

        Screening screening = new Screening(
                screeningRequestDto.getMovieId(), startTime, endTime, DEFAULT_SEATS, screeningRequestDto.getTheater()
        );

        screeningRepository.save(screening);
        return screening;
    }

    public Screening getScreening(Long id) {
        return screeningRepository.findById(id);
    }

    public Screening updateScreening(Long screeningId, ScreeningRequestDto screeningRequestDto) {

        Screening screening = screeningRepository.findById(screeningId);
        if (screening == null) {
            throw new IllegalArgumentException("상영 정보가 없습니다");
        }

        Movie movie = movieRepository.findById(screening.getMovieId());

        LocalDateTime newStart = LocalDateTime.parse(screeningRequestDto.getStartTime());
        LocalDateTime newEnd = newStart
                .plusMinutes(movie.getRunningTime())
                .plusMinutes(CLEANING_TIME);

        screening.setStartTime(newStart);
        screening.setEndTime(newEnd);
        screening.setTheater(screeningRequestDto.getTheater());

        screeningRepository.update(screening);

        return screening;
    }

    public void deleteScreening(Long screeningId) {
        Screening screening = screeningRepository.findById(screeningId);

        if (screening == null) {
            throw new IllegalArgumentException("상영 정보가 없습니다");
        }

        screeningRepository.delete(screeningId);
    }

    public List<Screening> getScreeningsByMovieId(Long movieId) {
        return screeningRepository.findAllByMovieId(movieId);
    }
}
