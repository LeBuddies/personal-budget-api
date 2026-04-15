package com.lebuddies.budget.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class AuthDto {

    @Getter
    public static class SignupRequest {
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        private String password;

        @NotBlank(message = "이름은 필수입니다.")
        private String name;
    }

    @Getter
    public static class LoginRequest {
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password;
    }

    @Getter
    public static class LoginResponse {
        private final String accessToken;
        private final String email;
        private final String name;

        public LoginResponse(String accessToken, String email, String name) {
            this.accessToken = accessToken;
            this.email = email;
            this.name = name;
        }
    }

    @Getter
    public static class SignupResponse {
        private final Long id;
        private final String email;
        private final String name;

        public SignupResponse(Long id, String email, String name) {
            this.id = id;
            this.email = email;
            this.name = name;
        }
    }
}
