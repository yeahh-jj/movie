package com.yeahhjj.movie.controller.api;

import com.yeahhjj.movie.domain.Movie;
import com.yeahhjj.movie.dto.movie.MovieRequestDto;
import com.yeahhjj.movie.service.MovieService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
public class MovieApiController {

    private final MovieService movieService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody MovieRequestDto movieRequestDto, HttpSession session) {

        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            throw new IllegalArgumentException("영화 등록은 관리자만 가능합니다.");
        }

        Movie movie = movieService.register(movieRequestDto);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {

        List<Movie> movies = movieService.getMovies();

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovie(@PathVariable Long id) {

        Movie movie = movieService.getMovie(id);

        return ResponseEntity.ok(movie);
    }

    @PatchMapping("/{movieId}")
    public ResponseEntity<?> update(@PathVariable Long movieId, @RequestBody MovieRequestDto movieRequestDto, HttpSession session) {

        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            throw new IllegalArgumentException("관리자만 수정 가능합니다.");
        }

        Movie updated = movieService.updateMovie(movieId, movieRequestDto);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<?> delete(@PathVariable Long movieId, HttpSession session) {

        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            throw new IllegalArgumentException("관리자만 삭제 가능합니다.");
        }

        movieService.deleteMovie(movieId);

        return ResponseEntity.ok("영화가 삭제되었습니다.");
    }
}
