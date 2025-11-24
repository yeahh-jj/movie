package com.yeahhjj.movie.repository;

import com.yeahhjj.movie.domain.Reservation;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationRepository {

    void save(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findByUserId(Long userId);

    void delete(Long id);
}
