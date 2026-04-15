package com.lebuddies.budget.domain.transaction.dto;

import com.lebuddies.budget.domain.transaction.entity.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDate;

public class TransactionDto {

    @Getter
    public static class Request {
        @NotNull(message = "거래 유형은 필수입니다. (INCOME 또는 EXPENSE)")
        private TransactionType type;

        @NotNull(message = "금액은 필수입니다.")
        @Positive(message = "금액은 양수여야 합니다.")
        private Long amount;

        private String description;
        private Long categoryId;

        @NotNull(message = "거래 날짜는 필수입니다.")
        private LocalDate transactionDate;
    }

    @Getter
    public static class Response {
        private final Long id;
        private final TransactionType type;
        private final Long amount;
        private final String description;
        private final Long categoryId;
        private final String categoryName;
        private final LocalDate transactionDate;

        public Response(Long id, TransactionType type, Long amount, String description,
                        Long categoryId, String categoryName, LocalDate transactionDate) {
            this.id = id;
            this.type = type;
            this.amount = amount;
            this.description = description;
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.transactionDate = transactionDate;
        }
    }
}
