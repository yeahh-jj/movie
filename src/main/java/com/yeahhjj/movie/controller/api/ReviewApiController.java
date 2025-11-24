package com.yeahhjj.movie.controller.api;

import com.yeahhjj.movie.domain.Review;
import com.yeahhjj.movie.dto.review.ReviewRequestDto;
import com.yeahhjj.movie.service.ReviewService;
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
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping("/register")
    public ResponseEntity<?> registerReview(@RequestBody ReviewRequestDto reviewRequestDto, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        reviewService.createReview(
                reviewRequestDto.getReservationId(),
                userId,
                reviewRequestDto.getRating(),
                reviewRequestDto.getContent()
        );

        return ResponseEntity.ok("리뷰가 등록되었습니다.");
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Review>> getReviewsByMovie(@PathVariable Long movieId) {
        List<Review> reviews = reviewService.getReviewsByMovieId(movieId);
        return ResponseEntity.ok(reviews);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto reviewRequestDto, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        reviewService.updateReview(
                userId,
                reviewId,
                reviewRequestDto.getRating(),
                reviewRequestDto.getContent()
        );

        return ResponseEntity.ok("리뷰가 수정되었습니다.");
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        reviewService.deleteReview(userId, reviewId);

        return ResponseEntity.ok("리뷰가 삭제되었습니다.");
    }

    @GetMapping("/movie/{movieId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long movieId) {
        double avgRating = reviewService.getAverageRating(movieId);
        return ResponseEntity.ok(avgRating);
    }
}