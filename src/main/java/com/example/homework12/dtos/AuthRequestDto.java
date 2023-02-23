package com.example.homework12.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuthRequestDto {
    private String username;
    private String password;
}
