package com.yeahhjj.movie.repository;

import com.yeahhjj.movie.domain.Screening;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScreeningRepository {
    void save(Screening screening);

    Screening findById(Long id);

    void update(Screening screening);

    void delete(Long id);

    List<Screening> findAllByMovieId(Long movieId);

    void updateAvailableSeats(@Param("id") Long id, @Param("availableSeats") int availableSeats);
}
