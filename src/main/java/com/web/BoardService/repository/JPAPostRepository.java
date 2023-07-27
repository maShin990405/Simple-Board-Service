package com.web.BoardService.repository;

import com.web.BoardService.domain.Post;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class JPAPostRepository implements PostRepository {

    private final EntityManager em;

    public JPAPostRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Post save(Post p) {
        return null;
    }

    @Override
    public Post delete(Post p) {
        return null;
    }

    @Override
    public Post update(Post p){
        return null;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return null;
    }

    @Override
    public Optional<Post> findByAuthor(Long id) {
        return null;
    }

    @Override
    public Optional<Post> findByTitle(String title) {
        return null;
    }

    @Override
    public Collection<Post> findAll() {
        return null;
    }
}
