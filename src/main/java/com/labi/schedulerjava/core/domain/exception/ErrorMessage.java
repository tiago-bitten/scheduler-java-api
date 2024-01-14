package com.labi.schedulerjava.core.domain.exception;

import java.time.Instant;

public record ErrorMessage(
        Instant timestamp,
        String message,
        Integer status
) { }
