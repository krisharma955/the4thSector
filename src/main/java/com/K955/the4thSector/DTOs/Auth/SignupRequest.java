package com.K955.the4thSector.DTOs.Auth;

public record SignupRequest(
        String username,
        String email,
        String password,
        String bio
) {
}
