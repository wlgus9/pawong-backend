package com.back.repository;

import com.back.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findByOwnerId(UUID ownerId);
}
