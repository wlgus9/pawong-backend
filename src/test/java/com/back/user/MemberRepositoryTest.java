package com.back.user;

import com.back.domain.Member;
import com.back.global.common.UserRole;
import com.back.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void signup() {
        Member member = Member.builder()
                .email("test2@gmail.com")
                .password("1234")
                .userName("홍길동")
                .nickName("ee")
                .build();

        memberRepository.save(member);

        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());

        assertThat(findMember).isPresent();
        assertThat(findMember.get().getUserRole()).isEqualTo(UserRole.ROLE_USER);

    }
}
