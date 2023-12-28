package com.labi.schedulerjava.dtos;

public record CreateUserDto(
        String name,
        String email,
        String password
) {
}
