package no.haga.hellospring.jpa.dao;

import no.haga.hellospring.jpa.domain.Post;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
@SuppressWarnings("JpaQueryApiInspection")
public class HelloDAOImpl implements HelloDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = false)
    public void savePost(Post post) {
        entityManager.persist(post);
        entityManager.flush();
    }

    @Transactional(readOnly = true)
    public Post getPost(int id) {
        return entityManager.find(Post.class, id);
    }

    @Transactional(readOnly = true)
    public Post findBySubject(String subject) {
        TypedQuery<Post> query = entityManager.createQuery("from Post where subject = :subject", Post.class);
        return query.setParameter("subject", subject).getSingleResult();
    }

    @Transactional(readOnly = true)
    public Collection<Post> getAll() {
        TypedQuery<Post> query = entityManager.createQuery("from Post", Post.class);
        return query.getResultList();
    }
}
