package com.yeahhjj.movie.controller.api;

import com.yeahhjj.movie.domain.User;
import com.yeahhjj.movie.dto.user.LoginRequestDto;
import com.yeahhjj.movie.dto.user.LoginResponseDto;
import com.yeahhjj.movie.dto.user.RegisterRequest;
import com.yeahhjj.movie.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {

        User user = userService.register(
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getNickName()
        );

        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto,
                                                  HttpSession session)
    {
        User user = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        session.setAttribute("userId", user.getId());

        if(user.getEmail().equals("admin@admin.com") && user.getPassword().equals("adminpassword")) {
            session.setAttribute("isAdmin", true);
        }

        LoginResponseDto responseDto = new LoginResponseDto(
                user.getId(), user.getEmail(), user.getNickName()
        );

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(HttpSession session) {

        session.invalidate();

        return ResponseEntity.ok("로그아웃되었습니다.");
    }
}