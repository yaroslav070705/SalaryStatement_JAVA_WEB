package y.cloud.java.dao_models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import y.cloud.java.dto_models.EmployeePostHistoryRequest;
import y.cloud.java.salary_statement_models.Employee;
import y.cloud.java.salary_statement_models.EmployeePostHistory;
import y.cloud.java.salary_statement_models.EmployeePostHistoryPK;
import y.cloud.java.salary_statement_models.Post;

import java.util.List;

@Repository
public class EmployeePostHistoryDAO implements EmployeePostHistoryInterfaceDAO {

    @PersistenceContext
    private EntityManager entityManager;

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
