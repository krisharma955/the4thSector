package com.K955.the4thSector.Service.ImpL;

import com.K955.the4thSector.DTOs.Auth.AuthResponse;
import com.K955.the4thSector.DTOs.Auth.LoginRequest;
import com.K955.the4thSector.DTOs.Auth.RefreshTokenRequest;
import com.K955.the4thSector.DTOs.Auth.SignupRequest;
import com.K955.the4thSector.Entity.User;
import com.K955.the4thSector.Exception.BadRequestException;
import com.K955.the4thSector.Exception.ResourceNotFoundException;
import com.K955.the4thSector.Mapper.UserMapper;
import com.K955.the4thSector.Repository.UserRepository;
import com.K955.the4thSector.Security.JwtAuthUtil;
import com.K955.the4thSector.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpL implements UserService {

    private final UserRepository userRepository;
    private final JwtAuthUtil jwtAuthUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public AuthResponse signup(SignupRequest request) {
        Boolean exists = userRepository.existsByEmail(request.email());
        if(exists) throw new BadRequestException("User already exists with Email: " + request.email());

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .bio(request.bio())
                .build();
        user.setPassword(passwordEncoder.encode(request.password()));
        User saved = userRepository.save(user);

        String accessToken = jwtAuthUtil.generateAccessToken(saved);
        String refreshToken = jwtAuthUtil.generateRefreshToken(saved);

        return new AuthResponse(accessToken, refreshToken, userMapper.toUserProfileResponse(saved));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        } catch (Exception e) {
            throw new BadRequestException("Invalid Email or Password!");
        }

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException(request.email(), "User"));

        String accessToken = jwtAuthUtil.generateAccessToken(user);
        String refreshToken = jwtAuthUtil.generateRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken, userMapper.toUserProfileResponse(user));
    }

    @Override
    public AuthResponse refreshAccessToken(RefreshTokenRequest request) {
        return null;
    }

}
