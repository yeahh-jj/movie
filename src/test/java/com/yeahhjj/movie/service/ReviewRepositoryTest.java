// package com.yeahhjj.movie.service;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.assertThatThrownBy;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.BDDMockito.given;
// import static org.mockito.Mockito.never;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;

// import com.yeahhjj.movie.domain.Reservation;
// import com.yeahhjj.movie.domain.Review;
// import com.yeahhjj.movie.repository.ReservationRepository;
// import com.yeahhjj.movie.repository.ReviewRepository;
// import java.time.LocalDateTime;
// import java.util.Arrays;
// import java.util.Collections;
// import java.util.List;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// @ExtendWith(MockitoExtension.class)
// public class ReviewRepositoryTest {

//     @Mock
//     private ReviewRepository reviewRepository;

//     @Mock
//     private ReservationRepository reservationRepository;

//     @InjectMocks
//     private ReviewService reviewService;

//     @Test
//     void 리뷰_등록() {
//         Long userId = 1L;
//         Long reservationId = 2L;

//         Reservation reservation = new Reservation();
//         reservation.setId(reservationId);
//         reservation.setUserId(userId);
//         reservation.setScreeningId(3L);

//         given(reservationRepository.findById(reservationId)).willReturn(reservation);

//         given(reviewRepository.findByReservationId(reservationId)).willReturn(null);

//         reviewService.createReview(reservationId, userId, 4L, 5.0, "재밌다");

//         verify(reviewRepository, times(1)).save(any(Review.class));
//     }

//     @Test
//     void 예약_없는_경우_리뷰_등록_실패() {
//         Long reservationId = 1L;
//         Long userId = 2L;
//         Long movieId = 3L;

//         given(reservationRepository.findById(reservationId)).willReturn(null);

//         assertThatThrownBy(() -> reviewService.createReview(reservationId, userId, movieId, 5.0, "재밌다"))
//                 .isInstanceOf(IllegalArgumentException.class)
//                 .hasMessage("예매 정보를 찾을 수 없습니다.");

//         verify(reviewRepository, never()).save(any());
//     }

//     @Test
//     void 로그인한_회원의_예약이_아닐_경우_리뷰_등록_실패() {
//         Long reservationId = 1L;
//         Long userId = 2L;
//         Long movieId = 3L;

//         Reservation reservation = new Reservation();
//         reservation.setId(reservationId);
//         reservation.setUserId(20L);

//         given(reservationRepository.findById(reservationId)).willReturn(reservation);

//         assertThatThrownBy(() -> reviewService.createReview(reservationId, userId, movieId, 5.0, "재밌다"))
//                 .isInstanceOf(IllegalArgumentException.class)
//                 .hasMessage("영화 관람 후 리뷰를 작성할 수 있습니다.")
//         ;

//         verify(reviewRepository, never()).save(any());
//     }

//     @Test
//     void 리뷰_중복_등록_실패() {
//         Long reservationId = 1L;
//         Long userId = 2L;
//         Long movieId = 3L;

//         Reservation reservation = new Reservation();
//         reservation.setId(reservationId);
//         reservation.setUserId(userId);

//         given(reservationRepository.findById(reservationId)).willReturn(reservation);

//         Review existingReview = new Review();
//         existingReview.setReservationId(reservationId);
//         given(reviewRepository.findByReservationId(reservationId)).willReturn(existingReview);

//         assertThatThrownBy(() -> reviewService.createReview(reservationId, userId, movieId, 5.0, "재밌다"))
//                 .isInstanceOf(IllegalArgumentException.class)
//                 .hasMessage("이미 작성한 리뷰가 있습니다.");
//     }

//     @Test
//     void 리뷰_수정() {
//         Long userId = 1L;
//         Long reviewId = 2L;

//         Review existingReview = new Review();
//         existingReview.setId(reviewId);
//         existingReview.setReservationId(4L);
//         existingReview.setMovieId(5L);
//         existingReview.setUserId(userId);
//         existingReview.setContent("내용");
//         existingReview.setRating(0.5);
//         existingReview.setCreatedAt(LocalDateTime.now().minusHours(1));

//         given(reviewRepository.findById(reviewId)).willReturn(existingReview);

//         double newRating = 5.0;
//         String newContent = "새로운 내용";

//         reviewService.updateReview(userId, reviewId, newRating, newContent);

//         assertThat(existingReview.getRating()).isEqualTo(newRating);
//         assertThat(existingReview.getContent()).isEqualTo(newContent);

//         verify(reviewRepository, times(1)).update(existingReview);
//     }

//     @Test
//     void 리뷰_없어서_수정_실패() {
//         Long userId = 1L;
//         Long reviewId = 10L;

//         given(reviewRepository.findById(reviewId)).willReturn(null);

//         assertThatThrownBy(() -> reviewService.updateReview(userId, reviewId, 4.5, "수정"))
//                 .isInstanceOf(IllegalArgumentException.class)
//                 .hasMessage("리뷰를 찾을 수 없습니다.");

//         verify(reviewRepository, never()).update(any());
//     }

//     @Test
//     void 자신이_등록한_리뷰가_아닌_경우() {
//         Long userId = 1L;
//         Long reviewId = 2L;

//         Review existing = new Review();
//         existing.setId(reviewId);
//         existing.setUserId(10L);

//         given(reviewRepository.findById(reviewId)).willReturn(existing);

//         assertThatThrownBy(() -> reviewService.updateReview(userId, reviewId, 5.0, "수정"))
//                 .isInstanceOf(IllegalArgumentException.class)
//                 .hasMessage("다른 사람의 리뷰는 수정할 수 없습니다.");

//         verify(reviewRepository, never()).update(any());
//     }

//     @Test
//     void 리뷰_삭제() {
//         Long userId = 1L;
//         Long reviewId = 2L;

//         Review review = new Review();
//         review.setId(reviewId);
//         review.setUserId(userId);

//         given(reviewRepository.findById(reviewId)).willReturn(review);

//         reviewService.deleteReview(userId, reviewId);

//         verify(reviewRepository, times(1)).delete(reviewId);
//     }

//     @Test
//     void 리뷰가_없어서_삭제_실패() {
//         Long userId = 1L;
//         Long reviewId = 2L;

//         given(reviewRepository.findById(reviewId)).willReturn(null);

//         assertThatThrownBy(() -> reviewService.deleteReview(userId, reviewId))
//                 .isInstanceOf(IllegalArgumentException.class)
//                 .hasMessage("리뷰를 찾을 수 없습니다.");
//     }

//     @Test
//     void 본인의_리뷰가_아니라서_삭제_실패() {
//         Long userId = 1L;
//         Long reviewId = 2L;

//         Review review = new Review();
//         review.setUserId(2L);
//         review.setId(reviewId);

//         given(reviewRepository.findById(reviewId)).willReturn(review);

//         assertThatThrownBy(() -> reviewService.deleteReview(userId, reviewId))
//                 .isInstanceOf(IllegalArgumentException.class)
//                 .hasMessage("다른 사람의 리뷰는 삭제할 수 없습니다.");
//     }

//     @Test
//     void 리뷰_평점_평균_계산() {

//         Long movieId = 1L;

//         Review r1 = new Review();
//         r1.setRating(5.0);

//         Review r2 = new Review();
//         r2.setRating(4.0);

//         Review r3 = new Review();
//         r3.setRating(3.0);

//         List<Review> reviews = Arrays.asList(r1, r2, r3);

//         given(reviewRepository.findByMovieId(movieId)).willReturn(reviews);

//         double avgRating = reviewService.getAverageRating(movieId);

//         assertThat(avgRating).isEqualTo(4.0);
//     }

//     @Test
//     void 리뷰가_없으면_0() {

//         Long movieId = 1L;

//         given(reviewRepository.findByMovieId(movieId)).willReturn(Collections.emptyList());

//         double avgRating = reviewService.getAverageRating(movieId);

//         assertThat(avgRating).isEqualTo(0.0);
//     }
// }
