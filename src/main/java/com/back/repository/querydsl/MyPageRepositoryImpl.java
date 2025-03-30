package com.back.repository.querydsl;

import com.back.domain.Member;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.back.domain.QMember.member;
import static com.back.domain.QPet.pet;

@Repository
@RequiredArgsConstructor
public class MyPageRepositoryImpl {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<Member> findByEmailWithPets(String email) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(member.email.eq(email));

        // 키움이(userType = 2)일 경우만 pets를 포함하고, 그렇지 않으면 pets 없이 가져오기
        BooleanExpression petCondition = member.userType.eq(2).and(pet.isNotNull());

        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(member)
                        .leftJoin(member.pets, pet).fetchJoin()
                        .where(builder.or(petCondition)) // 조건 추가
                        .fetchOne()
        );
    }
}
