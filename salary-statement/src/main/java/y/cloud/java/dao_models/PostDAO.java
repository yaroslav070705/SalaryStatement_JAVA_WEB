package y.cloud.java.dao_models;

import javax.annotation.PostConstruct;
import y.cloud.java.dto_models.PostRequest;
import y.cloud.java.salary_statement_models.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class PostDAO implements PostInterfaceDAO{
    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaBuilder cb;

    @PostConstruct
    public void init() {
        cb = entityManager.getCriteriaBuilder();
    }

    @Override
    public Post findById(UUID id) {
        return entityManager.find(Post.class, id);
    }

    public Post findByName(String post_name) {
        CriteriaQuery<Post> query = cb.createQuery(Post.class);
        Root<Post> root = query.from(Post.class);

        query.select(root).where(cb.equal(root.get("post_name"), post_name));

        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public List<Post> findAll() {
        return entityManager
                .createQuery("SELECT p FROM Post p", Post.class)
                .getResultList();
    }

    @Transactional
    @Override
    public UUID insert(PostRequest req) {
        Post post = new Post(req);

        entityManager.persist(post);

        return post.getId();
    }

    @Transactional
    @Override
    public Post update(PostRequest req) {
        Post post = entityManager.find(Post.class, req.getPostId());

        post.setPostName(req.getPostName());
        post.setPayoutValue(req.getPayoutValue());

        return post;
    }

    @Transactional
    @Override
    public String getPostName(UUID id) {
        return findById(id).getPostName();
    }

}
