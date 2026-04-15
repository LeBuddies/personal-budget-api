package com.lebuddies.budget.domain.transaction.controller;

import com.lebuddies.budget.common.response.ApiResponse;
import com.lebuddies.budget.domain.transaction.dto.TransactionDto;
import com.lebuddies.budget.domain.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "거래 내역", description = "수입/지출 거래 내역 관리 API")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    @Operation(summary = "내 거래 내역 전체 조회")
    public ResponseEntity<ApiResponse<List<TransactionDto.Response>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok(transactionService.findAll()));
    }

    @PostMapping
    @Operation(summary = "거래 내역 등록")
    public ResponseEntity<ApiResponse<TransactionDto.Response>> create(
            @Valid @RequestBody TransactionDto.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("거래 내역이 등록되었습니다.", transactionService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "거래 내역 수정")
    public ResponseEntity<ApiResponse<TransactionDto.Response>> update(
            @PathVariable Long id,
            @Valid @RequestBody TransactionDto.Request request) {
        return ResponseEntity.ok(ApiResponse.ok("거래 내역이 수정되었습니다.", transactionService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "거래 내역 삭제")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("거래 내역이 삭제되었습니다.", null));
    }
}
