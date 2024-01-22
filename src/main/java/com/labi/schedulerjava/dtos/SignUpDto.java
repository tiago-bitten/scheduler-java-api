package com.labi.schedulerjava.dtos;

import java.util.List;

public record SignUpDto(
        String name,
        String email,
        String password,
        List<Long> ministries
) {
}