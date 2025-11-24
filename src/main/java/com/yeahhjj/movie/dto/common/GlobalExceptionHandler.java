package com.yeahhjj.movie.dto.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(IllegalArgumentException exception) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                exception.getMessage()
        );

        return ResponseEntity.badRequest().body(errorResponseDto);
    }
}
