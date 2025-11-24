package com.yeahhjj.movie.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.yeahhjj.movie.domain.User;
import com.yeahhjj.movie.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void 회원가입() {
        String email = "email@email.com";
        String password = "password";
        String nickName = "nickName";

        given(userRepository.findByEmail(email)).willReturn(null);

        userService.register(email, password, nickName);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void 중복_이메일로_회원가입_실패() {
        String email = "email";
        String password = "password";
        String nickName = "nickName";

        User existingUser = new User();
        existingUser.setEmail(email);

        given(userRepository.findByEmail(email)).willReturn(existingUser);

        assertThatThrownBy(() -> userService.register(email, password, nickName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 이메일은 사용하실 수 없습니다.");
    }

    @Test
    void 로그인() {
        String email = "email";
        String password = "password";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        given(userRepository.findByEmail(email)).willReturn(user);

        User userLogin = userService.login(email, password);

        assertThat(userLogin.getEmail()).isEqualTo(email);
    }

    @Test
    void 해당하는_이메일이_존재하지_않아_로그인_실패() {
        String email = "email";
        String password = "password";

        given(userRepository.findByEmail(email)).willReturn(null);

        assertThatThrownBy(() -> userService.login(email, password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 또는 비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 비밀번호가_일치하지_않아_로그인_실패() {
        String email = "email";

        User user = new User();
        user.setEmail(email);
        user.setPassword("password");

        given(userRepository.findByEmail(email)).willReturn(user);

        assertThatThrownBy(() -> userService.login(email, "wrongPassword"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 또는 비밀번호가 일치하지 않습니다.");
    }
}
