package com.back.domain;

import com.back.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Pet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Member owner;

    private String name;
    private Integer age;
    private String species;
    private String description;
    private String photo;

    @Builder
    public Pet(Member owner, String name, Integer age, String species, String description, String photo) {
        this.owner = owner;
        this.name = name;
        this.age = age;
        this.species = species;
        this.description = description;
        this.photo = photo;
    }
}
