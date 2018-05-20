package com.scalors.module.post.repository;

import com.scalors.module.post.entity.Post;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Stateless
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    private final int BATCH_SIZE = 5;

    public void save(Post post) {
        persistCustom(post);
        em.flush();
    }

    public Optional<Post> update(Post post) {
        return Optional.of(em.merge(post));
    }

    private long persistCustom(Post post) {
        em.persist(post);
        return post.getId();
    }

    public void savePostBatch(List<Post> list) {
        final List<Long> savedPosts = new ArrayList<>(list.size());
        int i = 0;
        for (Post p : list) {
            savedPosts.add(persistCustom(p));
            i++;
            if (i % BATCH_SIZE == 0) {
                em.flush();
                em.clear();
            }
        }
    }

    public Optional<Post> findById(long id) {
        return Optional.of(em.find(Post.class, id));
    }

    public Optional<List<Post>> findByName(String name) {
        Query query = em.createQuery("SELECT p FROM Post p WHERE p.name = :name");
        return Optional.of(query.setParameter("name", name).getResultList());
    }

    public Collection<Post> findAll() {
        Query query = em.createQuery("SELECT p FROM Post p");
        return (Collection<Post>) query.getResultList();
    }

    public int removeById(long id) {
        Query query = em.createQuery("DELETE FROM Post p WHERE p.id = :id");
        return query.setParameter("id", id).executeUpdate();
    }

    public int removeByName(String name) {
        Query query = em.createQuery("DELETE FROM Post p WHERE p.name = :name");
        return query.setParameter("name", name).executeUpdate();
    }
}
