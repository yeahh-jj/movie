package com.yeahhjj.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {

    @GetMapping("/")
    public String loginPage() {
        return "redirect:/movie/login.html";
    }
}
