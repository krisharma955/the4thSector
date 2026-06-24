package com.K955.the4thSector.DTOs.Auth;

import com.K955.the4thSector.DTOs.User.UserProfileResponse;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        UserProfileResponse user
) {
}
