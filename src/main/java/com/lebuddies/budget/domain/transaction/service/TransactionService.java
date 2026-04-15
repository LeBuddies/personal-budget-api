package com.lebuddies.budget.domain.transaction.service;

import com.lebuddies.budget.common.exception.BusinessException;
import com.lebuddies.budget.domain.category.entity.Category;
import com.lebuddies.budget.domain.category.repository.CategoryRepository;
import com.lebuddies.budget.domain.transaction.dto.TransactionDto;
import com.lebuddies.budget.domain.transaction.entity.Transaction;
import com.lebuddies.budget.domain.transaction.repository.TransactionRepository;
import com.lebuddies.budget.domain.user.entity.User;
import com.lebuddies.budget.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> BusinessException.unauthorized("인증 정보를 확인할 수 없습니다."));
    }

    private TransactionDto.Response toResponse(Transaction t) {
        return new TransactionDto.Response(
                t.getId(), t.getType(), t.getAmount(), t.getDescription(),
                t.getCategory() != null ? t.getCategory().getId() : null,
                t.getCategory() != null ? t.getCategory().getName() : null,
                t.getTransactionDate()
        );
    }

    public List<TransactionDto.Response> findAll() {
        User user = getCurrentUser();
        return transactionRepository.findByUserOrderByTransactionDateDesc(user)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public TransactionDto.Response create(TransactionDto.Request request) {
        User user = getCurrentUser();
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> BusinessException.notFound("카테고리를 찾을 수 없습니다."));
        }
        Transaction transaction = Transaction.builder()
                .user(user)
                .category(category)
                .type(request.getType())
                .amount(request.getAmount())
                .description(request.getDescription())
                .transactionDate(request.getTransactionDate())
                .build();
        return toResponse(transactionRepository.save(transaction));
    }

    @Transactional
    public TransactionDto.Response update(Long id, TransactionDto.Request request) {
        User user = getCurrentUser();
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("거래 내역을 찾을 수 없습니다."));
        if (!transaction.getUser().getId().equals(user.getId())) {
            throw BusinessException.forbidden("수정 권한이 없습니다.");
        }
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> BusinessException.notFound("카테고리를 찾을 수 없습니다."));
        }
        transaction.update(category, request.getType(), request.getAmount(),
                request.getDescription(), request.getTransactionDate());
        return toResponse(transaction);
    }

    @Transactional
    public void delete(Long id) {
        User user = getCurrentUser();
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("거래 내역을 찾을 수 없습니다."));
        if (!transaction.getUser().getId().equals(user.getId())) {
            throw BusinessException.forbidden("삭제 권한이 없습니다.");
        }
        transactionRepository.delete(transaction);
    }
}
