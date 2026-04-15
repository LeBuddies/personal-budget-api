package com.lebuddies.budget.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class CategoryDto {

    @Getter
    public static class Request {
        @NotBlank(message = "카테고리 이름은 필수입니다.")
        private String name;
        private String icon;
    }

    @Getter
    public static class Response {
        private final Long id;
        private final String name;
        private final String icon;

        public Response(Long id, String name, String icon) {
            this.id = id;
            this.name = name;
            this.icon = icon;
        }
    }
}
