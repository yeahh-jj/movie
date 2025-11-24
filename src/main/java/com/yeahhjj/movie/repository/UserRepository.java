package com.yeahhjj.movie.repository;

import com.yeahhjj.movie.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    void save(User user);
    User findByEmail(String email);
}
