package com.web.BoardService.repository;

import com.web.BoardService.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PostRepository {
    Post save(Post p);

    Post delete(Post p);

    Post update(Post p);

    Optional<Post> findById(Long id);
    Optional<Post> findByAuthor(Long id);
    Optional<Post> findByTitle(String title);
    Collection<Post> findAll();
}
