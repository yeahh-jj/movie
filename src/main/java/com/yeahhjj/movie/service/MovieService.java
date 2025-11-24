package com.yeahhjj.movie.service;

import com.yeahhjj.movie.domain.Genre;
import com.yeahhjj.movie.domain.Movie;
import com.yeahhjj.movie.dto.movie.MovieRequestDto;
import com.yeahhjj.movie.repository.MovieRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie register(MovieRequestDto movieRequestDto) {

        Movie movie = new Movie();
        movie.setTitle(movieRequestDto.getTitle());
        movie.setDirector(movieRequestDto.getDirector());
        movie.setGenre(Genre.valueOf(movieRequestDto.getGenre().toUpperCase()));
        movie.setDescription(movieRequestDto.getDescription());
        movie.setRunningTime(movieRequestDto.getRunningTime());

        movieRepository.save(movie);
        return movie;
    }

    public Movie getMovie(Long id) {

        Movie movie = movieRepository.findById(id);
        if(movie == null) {
            throw new IllegalArgumentException("해당 영화가 없습니다.");
        }

        return movie;
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public Movie updateMovie(Long id, MovieRequestDto movieRequestDto) {

        Movie movie = new Movie();
        if(movie == null) {
            throw new IllegalArgumentException("해당하는 영화가 없습니다.");
        }

        movie.setTitle(movieRequestDto.getTitle());
        movie.setDirector(movieRequestDto.getDirector());
        movie.setGenre(Genre.valueOf(movieRequestDto.getGenre().toUpperCase()));
        movie.setDescription(movieRequestDto.getDescription());
        movie.setRunningTime(movieRequestDto.getRunningTime());

        movieRepository.update(movie);
        return movie;
    }
}
