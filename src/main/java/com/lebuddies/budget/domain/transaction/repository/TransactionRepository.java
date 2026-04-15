package com.lebuddies.budget.domain.transaction.repository;

import com.lebuddies.budget.domain.transaction.entity.Transaction;
import com.lebuddies.budget.domain.transaction.entity.TransactionType;
import com.lebuddies.budget.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserOrderByTransactionDateDesc(User user);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
           "WHERE t.user = :user AND t.type = :type " +
           "AND YEAR(t.transactionDate) = :year AND MONTH(t.transactionDate) = :month")
    Long sumAmountByUserAndTypeAndYearAndMonth(
            @Param("user") User user,
            @Param("type") TransactionType type,
            @Param("year") int year,
            @Param("month") int month);

    @Query("SELECT t FROM Transaction t " +
           "WHERE t.user = :user AND t.type = 'EXPENSE' " +
           "AND YEAR(t.transactionDate) = :year AND MONTH(t.transactionDate) = :month")
    List<Transaction> findExpensesByUserAndYearAndMonth(
            @Param("user") User user,
            @Param("year") int year,
            @Param("month") int month);
}
