package dao_models;

import dto_models.PostRequest;
import salary_statement_models.Post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class PostDAO implements PostInterfaceDAO{
    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaBuilder cb = entityManager.getCriteriaBuilder();

    @Override
    public Post findById(UUID id) {
        return entityManager.find(Post.class, id);
    }

    public Post findByName(String name) {
        CriteriaQuery<Post> query = cb.createQuery(Post.class);
        Root<Post> root = query.from(Post.class);

        query.select(root).where(cb.equal(root.get("name"), name));

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
    public void insert(PostRequest req) {
        Post post = new Post(req);

        entityManager.persist(post);
    }

    @Transactional
    @Override
    public void update(PostRequest req) {
        Post post = entityManager.find(Post.class, req.getPostId());

        post.setPostName(req.getPostName());
        post.setPayoutValue(req.getPayoutValue());
    }

    @Transactional
    @Override
    public void delete(UUID id) {
    }
}
