package com.lebuddies.budget.domain.user.service;

import com.lebuddies.budget.common.exception.BusinessException;
import com.lebuddies.budget.domain.user.dto.AuthDto;
import com.lebuddies.budget.domain.user.entity.User;
import com.lebuddies.budget.domain.user.jwt.JwtTokenProvider;
import com.lebuddies.budget.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthDto.SignupResponse signup(AuthDto.SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw BusinessException.badRequest("이미 사용 중인 이메일입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();

        User savedUser = userRepository.save(user);
        return new AuthDto.SignupResponse(savedUser.getId(), savedUser.getEmail(), savedUser.getName());
    }

    public AuthDto.LoginResponse login(AuthDto.LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> BusinessException.unauthorized("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw BusinessException.unauthorized("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());
        return new AuthDto.LoginResponse(token, user.getEmail(), user.getName());
    }
}
