package com.lebuddies.budget.domain.statistics.controller;

import com.lebuddies.budget.common.response.ApiResponse;
import com.lebuddies.budget.domain.statistics.dto.StatisticsDto;
import com.lebuddies.budget.domain.statistics.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@Tag(name = "통계", description = "월별/카테고리별 지출 통계 API")
@SecurityRequirement(name = "bearerAuth")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/monthly")
    @Operation(summary = "월별 수입/지출 통계 조회",
               description = "특정 연월의 총 수입, 총 지출, 순이익을 조회합니다.")
    public ResponseEntity<ApiResponse<StatisticsDto.MonthlyResponse>> getMonthlyStatistics(
            @RequestParam int year,
            @RequestParam int month) {
        return ResponseEntity.ok(ApiResponse.ok(statisticsService.getMonthlyStatistics(year, month)));
    }

    @GetMapping("/category")
    @Operation(summary = "카테고리별 지출 비율 조회",
               description = "특정 연월의 카테고리별 지출 금액 및 비율을 조회합니다.")
    public ResponseEntity<ApiResponse<StatisticsDto.CategoryStatResponse>> getCategoryStatistics(
            @RequestParam int year,
            @RequestParam int month) {
        return ResponseEntity.ok(ApiResponse.ok(statisticsService.getCategoryStatistics(year, month)));
    }
}
