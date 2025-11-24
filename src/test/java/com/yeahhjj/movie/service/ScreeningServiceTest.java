//package com.yeahhjj.movie.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//import com.yeahhjj.movie.domain.Movie;
//import com.yeahhjj.movie.domain.Screening;
//import com.yeahhjj.movie.repository.MovieRepository;
//import com.yeahhjj.movie.repository.ScreeningRepository;
//import java.time.LocalDateTime;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//public class ScreeningServiceTest {
//
//    @Mock
//    private ScreeningRepository screeningRepository;
//
//    @Mock
//    private MovieRepository movieRepository;
//
//    @InjectMocks
//    private ScreeningService screeningService;
//
//    @Test
//    void 상영_등록() {
//        Long movieId = 1L;
//        int runningTime = 150;
//        int cleaningTime = 15;
//
//        Movie movie = new Movie();
//        movie.setId(movieId);
//        movie.setRunningTime(runningTime);
//
//        LocalDateTime startTime = LocalDateTime.of(2025, 1, 1, 14, 0);
//        LocalDateTime endTime = startTime.plusMinutes(runningTime + cleaningTime);
//
//        given(movieRepository.findById(movieId)).willReturn(movie);
//
//        Screening result = screeningService.register(movieId, startTime, "1관");
//
//        assertNotNull(result);
//        assertEquals(movieId, result.getMovieId());
//        assertEquals(startTime, result.getStartTime());
//        assertEquals(endTime, result.getEndTime());
//        assertEquals(16, result.getTotalSeats());
//
//        verify(screeningRepository).save(any(Screening.class));
//    }
//
//    @Test
//    void 상영_상세_조회() {
//        Long screeningId = 1L;
//
//        Screening screening = new Screening(
//                1L,
//                LocalDateTime.of(2025, 1, 1, 14, 0),
//                LocalDateTime.of(2025, 1, 1, 16, 30),
//                16,
//                "1관"
//        );
//        screening.setId(screeningId);
//
//        given(screeningRepository.findById(screeningId)).willReturn(screening);
//
//        Screening result = screeningService.getScreening(screeningId);
//
//        assertNotNull(result);
//        assertEquals(screeningId, result.getId());
//        assertEquals(16, result.getTotalSeats());
//
//        verify(screeningRepository, times(1)).findById(screeningId);
//    }
//
//    @Test
//    void 상영_정보_수정() {
//        Long screeningId = 1L;
//
//        Screening existingScreening = new Screening(
//                1L,
//                LocalDateTime.of(2025, 1, 1, 14, 0),
//                LocalDateTime.of(2025, 1, 1, 16, 30),
//                16,
//                "1관"
//        );
//        existingScreening.setId(screeningId);
//
//        given(screeningRepository.findById(screeningId)).willReturn(existingScreening);
//
//        LocalDateTime newStart = LocalDateTime.of(2025, 1, 1, 15, 0);
//        int newSeats = 15;
//
//        Movie movie = new Movie();
//        movie.setId(1L);
//        movie.setRunningTime(150);
//
//        given(movieRepository.findById(1L)).willReturn(movie);
//
//        screeningService.updateScreening(screeningId, newStart, newSeats);
//
//        assertEquals(newStart, existingScreening.getStartTime());
//
//        assertEquals(newSeats, existingScreening.getTotalSeats());
//
//        verify(screeningRepository).update(existingScreening);
//    }
//
//    @Test
//    void 상영_정보_삭제() {
//        Long screeningId = 1L;
//
//        Screening screening = new Screening(
//                1L,
//                LocalDateTime.of(2025, 1, 1, 14, 0),
//                LocalDateTime.of(2025, 1, 1, 16, 30),
//                16,
//                "1관"
//        );
//        screening.setId(screeningId);
//
//        given(screeningRepository.findById(screeningId)).willReturn(screening);
//
//        screeningService.deleteScreening(screeningId);
//
//        verify(screeningRepository).delete(screeningId);
//    }
//
//    @Test
//    void 상영_정보_목록() {
//        Long movieId = 1L;
//
//        Screening s1 = new Screening(
//                movieId,
//                LocalDateTime.of(2025, 1, 1, 14, 0),
//                LocalDateTime.of(2025, 1, 1, 16, 30),
//                16,
//                "1관"
//        );
//        s1.setId(1L);
//
//        Screening s2 = new Screening(
//                movieId,
//                LocalDateTime.of(2025, 1, 2, 15, 0),
//                LocalDateTime.of(2025, 1, 2, 17, 30),
//                16,
//                "2관"
//        );
//        s2.setId(2L);
//
//        List<Screening> screenings = List.of(s1, s2);
//
//        given(screeningRepository.findAllByMovieId(movieId)).willReturn(screenings);
//
//        List<Screening> result = screeningService.getScreeningsByMovieId(movieId);
//
//        assertEquals(2, result.size());
//        verify(screeningRepository).findAllByMovieId(movieId);
//    }
//}