package com.example.homework12.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuthResponseDto {
    private String token;
    public AuthResponseDto(String token) {
        this.token = token;
    }
}
