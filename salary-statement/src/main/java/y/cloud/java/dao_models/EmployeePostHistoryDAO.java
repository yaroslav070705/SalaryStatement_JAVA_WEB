package y.cloud.java.dao_models;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import y.cloud.java.dto_models.EmployeePostHistoryRequest;
import y.cloud.java.dto_models.EmployeePostHistoryResponse;
import y.cloud.java.models_utils.NotStated;
import y.cloud.java.salary_statement_models.Employee;
import y.cloud.java.salary_statement_models.EmployeePostHistory;
import y.cloud.java.salary_statement_models.EmployeePostHistoryPK;
import y.cloud.java.salary_statement_models.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class EmployeePostHistoryDAO implements EmployeePostHistoryInterfaceDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaBuilder cb;

    @PostConstruct
    public void init() {
        cb = entityManager.getCriteriaBuilder();
    }

    @Override
    public EmployeePostHistory findById(EmployeePostHistoryPK id) {
        return entityManager.find(EmployeePostHistory.class, id);
    }

    @Override
    public List<EmployeePostHistory> findAll() {
        return entityManager
                .createQuery("SELECT eph FROM EmployeePostHistory eph", EmployeePostHistory.class)
                .getResultList();
    }

    @Transactional
    @Override
    public List<EmployeePostHistory> findByParams(EmployeePostHistoryRequest req) {
        CriteriaQuery<EmployeePostHistory> query = cb.createQuery(EmployeePostHistory.class);
        Root<EmployeePostHistory> root = query.from(EmployeePostHistory.class);
        List<Predicate> predicates = new ArrayList<>();

        if(req.getEmployeeId() != null) {
            predicates.add(cb.equal(root.get("employee_id").get("employee_id"), req.getEmployeeId()));
        }
        if(req.getPostId() != null) {
            predicates.add(cb.equal(root.get("post_id").get("post_id"), req.getPostId()));
        }
        if(req.getStartDate() != null) {
            predicates.add(cb.equal(root.get("start_date"), req.getStartDate()));
        }
        if(req.getEndDate() != null) {
            predicates.add(cb.equal(root.get("end_date"), req.getEndDate()));
        }

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    @Override
    public EmployeePostHistoryPK insert(EmployeePostHistoryRequest req) {
        EmployeePostHistory entity = new EmployeePostHistory(req);

        Employee employee = entityManager.find(Employee.class, req.getEmployeeId());
        Post post = entityManager.find(Post.class, req.getPostId());
        entity.setId(employee, post);

        entityManager.persist(entity);

        return entity.getId();
    }

    @Transactional
    @Override
    public EmployeePostHistory update(EmployeePostHistoryRequest req) {
        EmployeePostHistory entity = entityManager.find(
                EmployeePostHistory.class,
                new EmployeePostHistoryPK(req.getEmployeeId(), req.getPostId())
        );

        entity.setStartDate(req.getStartDate());
        entity.setEndDate(req.getEndDate());

        return entity;
    }

}
