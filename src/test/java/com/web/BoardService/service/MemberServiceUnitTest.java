package com.web.BoardService.service;

import com.web.BoardService.domain.Member;
import com.web.BoardService.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class MemberServiceUnitTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void regular_join_1() {

    }

    @Test
    public void regular_join_2() {

    }

    @Test
    public void regular_join_3() {

    }
}
