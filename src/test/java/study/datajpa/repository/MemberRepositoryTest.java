package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    void testMember() {
        System.out.println("memberRepository = " + memberRepository.getClass());
        Member member = new Member("jihan");

        Member savedMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(savedMember.getId()).orElseThrow();

        //  assertj
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        // jupiter
        assertEquals(findMember.getId(),member.getId());
        assertEquals(findMember.getUsername(),member.getUsername());
        assertEquals(findMember, member);
    }

}
