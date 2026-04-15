package com.lebuddies.budget.domain.category.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String icon;

    @Builder
    public Category(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public void update(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }
}
