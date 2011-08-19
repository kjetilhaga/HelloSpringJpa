package no.haga.hellospring.jpa.dao;

import no.haga.hellospring.jpa.domain.Post;
import org.springframework.orm.jpa.JpaTemplate;

import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Date;

public interface HelloDAO {

    void savePost(Post post);
    Post getPost(int id);
    Post findBySubject(String subject);
    Collection<Post> getAll();

}
