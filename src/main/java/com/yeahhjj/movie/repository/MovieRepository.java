package com.yeahhjj.movie.repository;

import com.yeahhjj.movie.domain.Movie;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MovieRepository {
    void save(Movie movie);
    Movie findById(Long id);
    List<Movie> findAll();
    void deleteById(Long id);
    void update(Movie movie);
}
