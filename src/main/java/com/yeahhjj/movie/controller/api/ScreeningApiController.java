package com.yeahhjj.movie.controller.api;

import com.yeahhjj.movie.domain.Screening;
import com.yeahhjj.movie.dto.screening.ScreeningRequestDto;
import com.yeahhjj.movie.service.ScreeningService;
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
@RequestMapping("/api/screenings")
@RequiredArgsConstructor
public class ScreeningApiController {

    private final ScreeningService screeningService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ScreeningRequestDto screeningRequestDto, HttpSession session) {

        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            throw new IllegalArgumentException("관리자만 상영 등록이 가능합니다.");
        }

        Screening screening = screeningService.register(screeningRequestDto);

        return ResponseEntity.ok(screening);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<?> getScreeningsByMovieId(@PathVariable Long movieId) {

        List<Screening> screenings = screeningService.getScreeningsByMovieId(movieId);

        return ResponseEntity.ok(screenings);
    }

    @PatchMapping("/{screeningId}")
    public ResponseEntity<?> update(@PathVariable Long screeningId, @RequestBody ScreeningRequestDto screeningRequestDto,
                                    HttpSession session) {

        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            throw new IllegalArgumentException("관리자만 상영 수정이 가능합니다.");
        }

        Screening updated = screeningService.updateScreening(screeningId, screeningRequestDto);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{screeningId}")
    public ResponseEntity<?> delete(@PathVariable Long screeningId, HttpSession session) {

        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            throw new IllegalArgumentException("관리자만 삭제 가능합니다.");
        }

        screeningService.deleteScreening(screeningId);

        return ResponseEntity.ok("상영 정보가 삭제되었습니다.");
    }

    @GetMapping("/{screeningId}")
    public ResponseEntity<?> getScreening(@PathVariable Long screeningId) {

        Screening screening = screeningService.getScreening(screeningId);

        return ResponseEntity.ok(screening);
    }

}
