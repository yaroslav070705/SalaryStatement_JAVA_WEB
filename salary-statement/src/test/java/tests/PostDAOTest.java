package tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import y.cloud.java.App;
import y.cloud.java.dto_models.PostRequest;
import y.cloud.java.salary_statement_models.Post;

import java.util.Objects;
import java.util.UUID;

@SpringBootTest(classes = App.class)
public class PostDAOTest extends DaoTestSupport {

    @Test
    public void insertPostDAOTest() {
        PostRequest req = new PostRequest();
        req.setPostName("PostDAO Insert");
        req.setPayoutValue(10000.0);

        UUID id = post_dao.insert(req);

        assert Objects.nonNull(post_dao.findById(id));
    }

    @Test
    public void updatePostDAOTest() {
        PostRequest req = new PostRequest();
        req.setPostName("PostDAO Updated");
        req.setPayoutValue(15000.0);
        UUID id = post_dao.insert(req);
        req.setPayoutValue(20000.0);
        req.setPostId(id);
        post_dao.update(req);

        Post post = post_dao.findById(id);

        assert Objects.equals(post.getPayoutValue(), 20000.0);
    }

    @Test
    public void findAllPostDAOTest() {
        PostRequest req = new PostRequest();
        req.setPostName("PostDAO Updated");
        req.setPayoutValue(15000.0);
        post_dao.insert(req);

        assert !post_dao.findAll().isEmpty();
    }
}
