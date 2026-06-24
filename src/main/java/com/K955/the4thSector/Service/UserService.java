package com.K955.the4thSector.Service;

import com.K955.the4thSector.DTOs.Auth.AuthResponse;
import com.K955.the4thSector.DTOs.Auth.LoginRequest;
import com.K955.the4thSector.DTOs.Auth.RefreshTokenRequest;
import com.K955.the4thSector.DTOs.Auth.SignupRequest;
import jakarta.validation.Valid;

public interface UserService {
    AuthResponse signup(@Valid SignupRequest request);

    AuthResponse login(@Valid LoginRequest request);

    AuthResponse refreshAccessToken(@Valid RefreshTokenRequest request);
}
