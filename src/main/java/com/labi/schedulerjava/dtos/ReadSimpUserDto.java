package com.labi.schedulerjava.dtos;

public record ReadSimpUserDto(
        Long id,
        String name,
        String email,
        Boolean isApproved,
        Boolean isSuperUser
) {
}
