package com.lebuddies.budget.domain.category.service;

import com.lebuddies.budget.common.exception.BusinessException;
import com.lebuddies.budget.domain.category.dto.CategoryDto;
import com.lebuddies.budget.domain.category.entity.Category;
import com.lebuddies.budget.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto.Response> findAll() {
        return categoryRepository.findAll().stream()
                .map(c -> new CategoryDto.Response(c.getId(), c.getName(), c.getIcon()))
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryDto.Response create(CategoryDto.Request request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw BusinessException.badRequest("이미 존재하는 카테고리 이름입니다.");
        }
        Category category = Category.builder()
                .name(request.getName())
                .icon(request.getIcon())
                .build();
        Category saved = categoryRepository.save(category);
        return new CategoryDto.Response(saved.getId(), saved.getName(), saved.getIcon());
    }

    @Transactional
    public CategoryDto.Response update(Long id, CategoryDto.Request request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("카테고리를 찾을 수 없습니다."));
        category.update(request.getName(), request.getIcon());
        return new CategoryDto.Response(category.getId(), category.getName(), category.getIcon());
    }

    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> BusinessException.notFound("카테고리를 찾을 수 없습니다."));
        categoryRepository.delete(category);
    }
}
