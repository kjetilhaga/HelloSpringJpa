package no.haga.hellospring.jpa.tests.dao;

import net.sf.cglib.core.MethodWrapper;
import no.haga.hellospring.jpa.dao.HelloDAO;
import no.haga.hellospring.jpa.domain.Post;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.Console;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-persistenceProvider.xml")
@Transactional
public class HelloDAOImplTests {

    @Resource
    protected HelloDAO helloDAO;

    @Autowired
    protected DataSource dataSource;


    @BeforeTransaction
    public void beforeTransaction() throws DatabaseUnitException, IOException, SQLException {
        Connection con = DataSourceUtils.getConnection(dataSource);
        IDatabaseConnection dbUnitCon = new DatabaseConnection(con);
        IDataSet dataSet = new XmlDataSet(this.getClass().getClassLoader().getResourceAsStream("datasets/initial.xml"));
        try {
            DatabaseOperation.CLEAN_INSERT.execute(dbUnitCon, dataSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }

    @Test
    public void savePost_withValidPost_shouldBePersisted() {
        Post post = new Post();
        post.setSubject("new subject");
        post.setBody("new body");
        helloDAO.savePost(post);
        Post retrievedPost = helloDAO.getPost(post.getId());
        assertNotNull(retrievedPost);
    }

    @Test
    public void getPost_byExistingId_shouldReturnValidPost() {
        Post post = helloDAO.getPost(1);
        assertNotNull(post);
    }

    @Test
    public void getPost_withUnknownId_shouldReturnNull() {
        Post post = helloDAO.getPost(-1);
        assertNull(post);
    }

    @Test
    public void findBySubject_withExistingSubject_shouldReturnValidPost() {
        Post post = helloDAO.findBySubject("hello spring");
        assertNotNull(post);
    }

    @Test
    public void getAll_fromTestDataset_shouldReturnTwoPosts() {
        Collection<Post> posts = helloDAO.getAll();
        assertEquals(2, posts.size());
    }

}
