package no.haga.hellospring.jpa.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "POSTS")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_ID")
    private int id;

    @Column(name = "SUBJECT", length = 50)
    private String subject;

    @Column(name = "BODY", length = 2000)
    private String body;

    @Column(name = "POSTED")
    @Temporal(TemporalType.DATE)
    private Date posted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getPosted() {
        return posted;
    }

    public void setPosted(Date posted) {
        this.posted = posted;
    }
}
