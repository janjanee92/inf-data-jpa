package study.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import study.datajpa.repository.MemberRepository;
import study.datajpa.repository.TeamRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;

    @Test
    void testEntity() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        System.out.println("=========A team : " + teamA.getMembers());
        System.out.println("=========B team : " + teamB.getMembers());

        member1.changeTeam(teamB);

        System.out.println("=========A team : " + teamA.getMembers());
        System.out.println("=========B team : " + teamB.getMembers());

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);


        //  초기화
        em.flush();
        em.clear();

        List<Team> teams = em.createQuery("select t from Team  t", Team.class)
                .getResultList();

        for (Team team : teams) {
            System.out.println("=======find team member:=====" + team.getMembers());
        }


        //  확인
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("-> member.team = " + member.getTeam());
        }

    }
    
    @Test
    void JpaEventBaseEntity() throws Exception {
        //  given
        Member member = new Member("member1");
        memberRepository.save(member);  //  @PrePersist

        Thread.sleep(1000L);
        member.setUsername("member2");

        em.flush(); //  @PreUpdate
        em.clear();
        //  when
        Member findMember = memberRepository.findById(member.getId()).orElseThrow();

        //  then

        Team team = new Team("teamA");
        teamRepository.save(team);  //  @PrePersist

        Thread.sleep(2000L);
        team.setName("teamB");  //  @PreUpdate

        em.flush();
        em.clear();

        Team findTeam = teamRepository.findById(team.getId()).orElseThrow();

    }
}