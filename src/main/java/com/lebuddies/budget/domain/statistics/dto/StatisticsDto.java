package com.lebuddies.budget.domain.statistics.dto;

import lombok.Getter;

import java.util.List;

public class StatisticsDto {

    @Getter
    public static class MonthlyResponse {
        private final int year;
        private final int month;
        private final Long totalIncome;
        private final Long totalExpense;
        private final Long netAmount;

        public MonthlyResponse(int year, int month, Long totalIncome, Long totalExpense) {
            this.year = year;
            this.month = month;
            this.totalIncome = totalIncome;
            this.totalExpense = totalExpense;
            this.netAmount = totalIncome - totalExpense;
        }
    }

    @Getter
    public static class CategoryStatResponse {
        private final int year;
        private final int month;
        private final List<CategoryItem> categories;

        public CategoryStatResponse(int year, int month, List<CategoryItem> categories) {
            this.year = year;
            this.month = month;
            this.categories = categories;
        }
    }

    @Getter
    public static class CategoryItem {
        private final String categoryName;
        private final Long amount;
        private final double percentage;

        public CategoryItem(String categoryName, Long amount, double percentage) {
            this.categoryName = categoryName;
            this.amount = amount;
            this.percentage = percentage;
        }
    }
}
