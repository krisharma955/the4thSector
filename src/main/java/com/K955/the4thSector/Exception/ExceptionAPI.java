package com.K955.the4thSector.Exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ExceptionAPI(
        HttpStatus status,
        String message,
        Instant timestamp
) {
}
