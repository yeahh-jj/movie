package com.yeahhjj.movie.repository;

import com.yeahhjj.movie.domain.Review;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewRepository {
    void save(Review review);

    Review findByReservationId(Long reservationId);

    Review findById(Long id);

    void update(Review review);

    void delete(Long id);

    List<Review> findByMovieId(Long movieId);
}
