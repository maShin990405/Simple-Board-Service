package com.web.BoardService.repository;

import com.web.BoardService.domain.Post;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
public class JPAPostRepositoryUnitTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void testSave1() {
        //given
        Post post = new Post();
        post.setAuthor_id(1L);
        post.setContext("TEST1 CONTEXT");
        post.setTitle("TEST1");

        //when
        postRepository.save(post);

        //Then
        Post compare = postRepository.findById(post.getId()).get();
        assertEquals(compare.getId(), post.getId());
        assertEquals(compare.getAuthor_id(), post.getAuthor_id());
        assertEquals(compare.getContext(), post.getContext());
        assertEquals(compare.getTitle(), post.getTitle());
    }

    @Test
    public void testSave2() {
        //given
        Post post1 = new Post();
        Post post2 = new Post();

        post1.setAuthor_id(1L);
        post1.setContext("TEST1 CONTEXT");
        post1.setTitle("TEST1");

        post2.setAuthor_id(2L);
        post2.setContext("TEST2 CONTEXT");
        post2.setTitle("TEST2");

        //when
        postRepository.save(post1);
        postRepository.save(post2);

        //Then
        Post compare1 = postRepository.findByAuthor(post1.getAuthor_id()).get();
        Post compare2 = postRepository.findByTitle(post2.getTitle()).get();

        assertEquals(compare1.getId(), post1.getId());
        assertEquals(compare1.getAuthor_id(), post1.getAuthor_id());
        assertEquals(compare1.getContext(), post1.getContext());
        assertEquals(compare1.getTitle(), post1.getTitle());

        assertEquals(compare2.getId(), post2.getId());
        assertEquals(compare2.getAuthor_id(), post2.getAuthor_id());
        assertEquals(compare2.getContext(), post2.getContext());
        assertEquals(compare2.getTitle(), post2.getTitle());

    }

    @Test
    public void testFindAll() {
        //Given
        Post post1 = new Post();
        Post post2 = new Post();
        Post post3 = new Post();

        //When
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        //then
        ArrayList<Post> result = (ArrayList<Post>) postRepository.findAll();
        assertEquals(result.get(0).getId(), post1.getId());
        assertEquals(result.get(1).getId(), post2.getId());
        assertEquals(result.get(2).getId(), post3.getId());
    }
}
