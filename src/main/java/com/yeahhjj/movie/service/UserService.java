package com.yeahhjj.movie.service;

import com.yeahhjj.movie.domain.User;
import com.yeahhjj.movie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(String email, String password, String nickName) {

        User existingEmail = userRepository.findByEmail(email);
        if (existingEmail != null) {
            throw new IllegalArgumentException("중복된 이메일은 사용하실 수 없습니다.");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setNickName(nickName);

        userRepository.save(user);

        return user;
    }

    public User login(String email, String password) {

        User user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}
