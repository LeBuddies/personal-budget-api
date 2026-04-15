package com.lebuddies.budget.domain.statistics.service;

import com.lebuddies.budget.common.exception.BusinessException;
import com.lebuddies.budget.domain.statistics.dto.StatisticsDto;
import com.lebuddies.budget.domain.transaction.entity.Transaction;
import com.lebuddies.budget.domain.transaction.entity.TransactionType;
import com.lebuddies.budget.domain.transaction.repository.TransactionRepository;
import com.lebuddies.budget.domain.user.entity.User;
import com.lebuddies.budget.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> BusinessException.unauthorized("인증 정보를 확인할 수 없습니다."));
    }

    public StatisticsDto.MonthlyResponse getMonthlyStatistics(int year, int month) {
        User user = getCurrentUser();
        Long totalIncome = transactionRepository
                .sumAmountByUserAndTypeAndYearAndMonth(user, TransactionType.INCOME, year, month);
        Long totalExpense = transactionRepository
                .sumAmountByUserAndTypeAndYearAndMonth(user, TransactionType.EXPENSE, year, month);
        return new StatisticsDto.MonthlyResponse(year, month,
                totalIncome == null ? 0L : totalIncome,
                totalExpense == null ? 0L : totalExpense);
    }

    public StatisticsDto.CategoryStatResponse getCategoryStatistics(int year, int month) {
        User user = getCurrentUser();
        List<Transaction> expenses = transactionRepository
                .findExpensesByUserAndYearAndMonth(user, year, month);

        long totalExpense = expenses.stream().mapToLong(Transaction::getAmount).sum();

        Map<String, Long> categoryAmounts = expenses.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getCategory() != null ? t.getCategory().getName() : "미분류",
                        Collectors.summingLong(Transaction::getAmount)
                ));

        List<StatisticsDto.CategoryItem> items = categoryAmounts.entrySet().stream()
                .map(e -> new StatisticsDto.CategoryItem(
                        e.getKey(),
                        e.getValue(),
                        totalExpense > 0 ? (double) e.getValue() / totalExpense * 100 : 0.0
                ))
                .sorted((a, b) -> Long.compare(b.getAmount(), a.getAmount()))
                .collect(Collectors.toList());

        return new StatisticsDto.CategoryStatResponse(year, month, items);
    }
}
