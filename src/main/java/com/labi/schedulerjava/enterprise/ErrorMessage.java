package com.labi.schedulerjava.enterprise;

import java.time.Instant;

public record ErrorMessage(
        Instant timestamp,
        String message,
        Integer status
) { }
