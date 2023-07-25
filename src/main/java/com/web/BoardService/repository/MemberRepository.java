package com.web.BoardService.repository;

import com.web.BoardService.domain.Member;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByUsername(String username);

    Optional<Member> findByNickname(String nickname);
    List<Member> findAll();

}
