package dao_models;

import dto_models.PostRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import salary_statement_models.Post;

import java.util.List;
import java.util.UUID;

@Repository
public class PostDAO implements PostInterfaceDAO{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Post findById(UUID id) {
        return entityManager.find(Post.class, id);
    }

    @Override
    public List<Post> findAll() {
        return entityManager
                .createQuery("SELECT p FROM Post p", Post.class)
                .getResultList();
    }

    @Transactional
    @Override
    public void insert(PostRequest req) {
        Post post = new Post(req);

        entityManager.persist(post);
    }

    @Transactional
    @Override
    public void update(PostRequest req) {
        Post post = entityManager.find(Post.class, req.getId());

        post.setPostName(req.getPostName());
        post.setPayoutValue(req.getPayoutValue());
    }

    @Transactional
    @Override
    public void delete(UUID id) {
    }
}
