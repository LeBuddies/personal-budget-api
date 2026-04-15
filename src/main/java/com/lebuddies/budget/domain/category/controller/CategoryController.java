package com.lebuddies.budget.domain.category.controller;

import com.lebuddies.budget.common.response.ApiResponse;
import com.lebuddies.budget.domain.category.dto.CategoryDto;
import com.lebuddies.budget.domain.category.service.CategoryService;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "카테고리", description = "카테고리 관리 API")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "카테고리 전체 조회")
    public ResponseEntity<ApiResponse<List<CategoryDto.Response>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok(categoryService.findAll()));
    }

    @PostMapping
    @Operation(summary = "카테고리 생성")
    public ResponseEntity<ApiResponse<CategoryDto.Response>> create(
            @Valid @RequestBody CategoryDto.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("카테고리가 생성되었습니다.", categoryService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "카테고리 수정")
    public ResponseEntity<ApiResponse<CategoryDto.Response>> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDto.Request request) {
        return ResponseEntity.ok(ApiResponse.ok("카테고리가 수정되었습니다.", categoryService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "카테고리 삭제")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("카테고리가 삭제되었습니다.", null));
    }
}
