package com.web.BoardService.repository;

import com.web.BoardService.domain.Post;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class JPAPostRepository implements PostRepository {

    private final EntityManager em;

    public JPAPostRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Post save(Post p) {
        em.persist(p);
        return p;
    }

    // TODO: Implement the delete operation once create/save feature is done.
    @Override
    public Post delete(Post p) {
        return null;
    }

    // TODO: Implement the update operation once create/save feature is done.
    @Override
    public Post update(Post p){
        return null;
    }

    @Override
    public Optional<Post> findById(Long id) {
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }

    @Override
    public Optional<Post> findByAuthor(Long author_id) {
        List<Post> result = em.createQuery("select p from Post p where p.author_id = :author_id", Post.class)
                .setParameter("author_id", author_id)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public Optional<Post> findByTitle(String title) {
        List<Post> result = em.createQuery("select p from Post p where p.title = :title", Post.class)
                .setParameter("title", title)
                .getResultList();

        return result.stream().findAny();

    }

    @Override
    public Collection<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }
}
