package com.back.repository;

import com.back.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MyPageRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByEmail(String email);

}
