//package com.yeahhjj.movie.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import com.yeahhjj.movie.domain.Genre;
//import com.yeahhjj.movie.domain.Movie;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//@SpringBootTest
//public class MovieServiceTest {
//
//    @Autowired
//    MovieService movieService;
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @BeforeEach
//    void setUp() {
//        jdbcTemplate.execute("DROP TABLE IF EXISTS movie");
//        jdbcTemplate.execute("CREATE TABLE movie (" +
//                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
//                "title VARCHAR(255)," +
//                "director VARCHAR(255)," +
//                "genre VARCHAR(255)," +
//                "description TEXT," +
//                "running_time INT" +
//                ")");
//    }
//
//    @Test
//    void 영화_등록() {
//        Movie movie = new Movie();
//        movie.setTitle("제목");
//        movie.setDirector("감독");
//        movie.setDescription("설명");
//        movie.setGenre(Genre.DRAMA);
//        movie.setRunningTime(120);
//
//        movieService.register(movie);
//
//        List<Movie> movies = movieService.getMovies();
//
//        assertThat(movies.size()).isEqualTo(1);
//        assertThat(movies.get(0).getTitle()).isEqualTo("제목");
//        assertThat(movies.get(0).getDirector()).isEqualTo("감독");
//        assertThat(movies.get(0).getDescription()).isEqualTo("설명");
//        assertThat(movies.get(0).getGenre()).isEqualTo(Genre.DRAMA);
//        assertThat(movies.get(0).getRunningTime()).isEqualTo(120);
//    }
//
//    @Test
//    void 영화_조회() {
//        Movie movie = new Movie();
//        movie.setTitle("제목");
//        movie.setDirector("감독");
//        movie.setDescription("설명");
//        movie.setGenre(Genre.DRAMA);
//        movie.setRunningTime(120);
//
//        movieService.register(movie);
//
//        Movie findMovie = movieService.getMovie(movie.getId());
//        assertThat(findMovie).isNotNull();
//        assertThat(findMovie.getTitle()).isEqualTo("제목");
//    }
//
//    @Test
//    void 영화_삭제() {
//        Movie movie = new Movie();
//        movie.setTitle("제목");
//        movie.setDirector("감독");
//        movie.setDescription("설명");
//        movie.setGenre(Genre.DRAMA);
//        movie.setRunningTime(120);
//
//        movieService.register(movie);
//
//        Long id = movie.getId();
//        assertNotNull(id);
//
//        movieService.deleteMovie(id);
//
//        List<Movie> movies = movieService.getMovies();
//        assertTrue(movies.isEmpty());
//    }
//
//    @Test
//    void 영화_수정() {
//        Movie movie = new Movie();
//        movie.setTitle("제목");
//        movie.setDirector("감독");
//        movie.setDescription("설명");
//        movie.setGenre(Genre.DRAMA);
//        movie.setRunningTime(120);
//
//        movieService.register(movie);
//
//        Long id = movie.getId();
//        assertNotNull(id);
//
//        movie.setTitle("수정된 제목");
//        movie.setRunningTime(150);
//
//        movieService.updateMovie(movie);
//
//        Movie movieById = movieService.getMovie(id);
//
//        assertThat(movieById.getTitle()).isEqualTo("수정된 제목");
//        assertThat(movieById.getRunningTime()).isEqualTo(150);
//    }
//}
