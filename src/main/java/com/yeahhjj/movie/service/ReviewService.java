package com.yeahhjj.movie.service;

import com.yeahhjj.movie.domain.Reservation;
import com.yeahhjj.movie.domain.Review;
import com.yeahhjj.movie.domain.Screening;
import com.yeahhjj.movie.repository.ReservationRepository;
import com.yeahhjj.movie.repository.ReviewRepository;
import com.yeahhjj.movie.repository.ScreeningRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final ScreeningRepository screeningRepository;

    public void createReview(Long reservationId, Long userId, Double rating, String content) {

        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("예매 정보를 찾을 수 없습니다.");
        }

        if (!reservation.getUserId().equals(userId)) {
            throw new IllegalArgumentException("영화 관람 후 리뷰를 작성할 수 있습니다.");
        }

        Review existingReview = reviewRepository.findByReservationId(reservationId);
        if (existingReview != null) {
            throw new IllegalArgumentException("이미 작성한 리뷰가 있습니다.");
        }

        Screening screening = screeningRepository.findById(reservation.getScreeningId());
        if (screening == null) {
            throw new IllegalArgumentException("상영 정보를 찾을 수 없습니다.");
        }

        Long movieId = screening.getMovieId();

        Review review = new Review();
        review.setReservationId(reservationId);
        review.setUserId(userId);
        review.setMovieId(movieId);
        review.setRating(rating);
        review.setContent(content);
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(null);

        reviewRepository.save(review);
    }

    public void updateReview(Long userId, Long reviewId, double rating, String content) {

        Review review = reviewRepository.findById(reviewId);
        if (review == null) {
            throw new IllegalArgumentException("리뷰를 찾을 수 없습니다.");
        }

        if (!review.getUserId().equals(userId)) {
            throw new IllegalArgumentException("다른 사람의 리뷰는 수정할 수 없습니다.");
        }

        review.setRating(rating);
        review.setContent(content);
        review.setUpdatedAt(LocalDateTime.now());

        reviewRepository.update(review);
    }

    public void deleteReview(Long userId, Long reviewId) {

        Review review = reviewRepository.findById(reviewId);
        if (review == null) {
            throw new IllegalArgumentException("리뷰를 찾을 수 없습니다.");
        }

        if (!review.getUserId().equals(userId)) {
            throw new IllegalArgumentException("다른 사람의 리뷰는 삭제할 수 없습니다.");
        }

        reviewRepository.delete(reviewId);
    }

    public double getAverageRating(Long movieId) {

        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Review review : reviews) {
            sum += review.getRating();
        }

        return Math.round((sum / reviews.size()) * 10) / 10.0;
    }

    public List<Review> getReviewsByMovieId(Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }
}
