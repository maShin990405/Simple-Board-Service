package com.web.BoardService.repository;

import com.web.BoardService.domain.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class JPAMemberRepositoryUnitTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void test_save() {
        //given
        Member member = new Member();
        member.setEmail("test1@gmail.com");
        member.setUsername("test1");
        member.setPassword("1234");
        member.setNickname("t1");

        //when
        Long userID = memberRepository.save(member).getId();

        //then
        Member compare = memberRepository.findById(userID).get();
        assertTrue(compare.equal(member));
    }

    @Test
    public void test_save2() {
        //given
        Member member = new Member();
        member.setEmail("test1@gmail.com");
        member.setUsername("test1");
        member.setPassword("1234");
        member.setNickname("t1");

        Member member2 = new Member();
        member.setEmail("test2@gmail.com");
        member.setUsername("test2");
        member.setPassword("1234");
        member.setNickname("t1");

        //when
        Long user1ID = memberRepository.save(member).getId();
        memberRepository.save(member2);

        //then
        Member compare = memberRepository.findById(user1ID).get();
        assertFalse(compare.equal(member2));
        assertTrue(compare.equal(member));
    }

    @Test
    public void test_save3() {
        //given
        Member member = new Member();
        member.setEmail("test1@gmail.com");
        member.setUsername("test1");
        member.setPassword("1234");
        member.setNickname("t1");

        Member member2 = new Member();
        member.setEmail("test1@gmail.com");
        member.setUsername("test1");
        member.setPassword("1234");
        member.setNickname("t1");

        //when
        Long user1ID = memberRepository.save(member).getId();
        memberRepository.save(member2);

        //then
        Member compare = memberRepository.findById(user1ID).get();
        assertFalse(compare.equal(member2));
        assertTrue(compare.equals(member));
    }

    @Test
    public void test_save4() {
        //given
        Member member = new Member();
        member.setEmail("test1@gmail.com");
        member.setUsername("test1");
        member.setPassword("1234");
        member.setNickname("t1");

        Member member2 = new Member();
        member.setEmail("test1@gmail.com");
        member.setUsername("test1");
        member.setPassword("1234");
        member.setNickname("t1");

        //when
        String email = memberRepository.save(member).getEmail();
        memberRepository.save(member2);

        //then
        Member compare = memberRepository.findByEmail(email).get();
        assertFalse(compare.equal(member2));
        assertTrue(compare.equals(member));
    }
}
