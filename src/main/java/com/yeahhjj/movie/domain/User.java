package com.yeahhjj.movie.domain;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String password;
    private String nickName;
}
