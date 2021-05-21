package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;


import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {
    @Autowired MemberJpaRepository memberJpaRepository;
    
    @Test
    void testMember () {
        //  given
        Member member = new Member("jihan");

        //  when
        Member savedMember = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.find(savedMember.getId());

        //  then
        //  assertj
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        // jupiter
        assertEquals(findMember.getId(),member.getId());
        assertEquals(findMember.getUsername(),member.getUsername());
        assertEquals(findMember, member);
    }

    @Test
    void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        //  단건 조회
        Member findMember1 = memberJpaRepository.findById(member1.getId()).orElseThrow();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).orElseThrow();
        assertEquals(findMember1, member1);
        assertEquals(findMember2, member2);

        //  리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        assertEquals(all.size(), 2);

        //  카운트 검증
        long count = memberJpaRepository.count();
        assertEquals(count, 2);

        //  삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        assertEquals(memberJpaRepository.count(), 0);

    }
}