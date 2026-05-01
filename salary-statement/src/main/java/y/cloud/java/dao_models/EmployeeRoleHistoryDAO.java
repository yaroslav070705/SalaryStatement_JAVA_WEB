package y.cloud.java.dao_models;

import y.cloud.java.dto_models.EmployeeRoleHistoryRequest;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import y.cloud.java.salary_statement_models.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRoleHistoryDAO implements EmployeeRoleHistoryInterfaceDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaBuilder cb;

    @PostConstruct
    public void init() {
        cb = entityManager.getCriteriaBuilder();
    }

    @Override
    public EmployeeRoleHistory findById(EmployeeRoleHistoryPK id) {
        return entityManager.find(EmployeeRoleHistory.class, id);
    }

    @Override
    public List<EmployeeRoleHistory> findAll() {
        return entityManager.createQuery("SELECT erh FROM EmployeeRoleHistory erh", EmployeeRoleHistory.class).getResultList();
    }

    @Override
    public List<EmployeeRoleHistory> findByParams(EmployeeRoleHistoryRequest req) {
        CriteriaQuery<EmployeeRoleHistory> query = cb.createQuery(EmployeeRoleHistory.class);
        Root<EmployeeRoleHistory> root = query.from(EmployeeRoleHistory.class);
        List<Predicate> predicates = new ArrayList<>();

        if(req.getProjectId() != null) {
            predicates.add(cb.equal(root.get("project_id").get("project_id"), req.getProjectId()));
        }
        if(req.getRoleId() != null) {
            predicates.add(cb.equal(root.get("role_id").get("role_id"), req.getRoleId()));
        }
        if(req.getEmployeeId() != null) {
            predicates.add(cb.equal(root.get("employee_id").get("employee_id"), req.getEmployeeId()));
        }

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    @Override
    public EmployeeRoleHistoryPK insert(EmployeeRoleHistoryRequest req) {
        EmployeeRoleHistory entity = new EmployeeRoleHistory(req);

        Employee employee = entityManager.find(Employee.class, req.getEmployeeId());
        Project project = entityManager.find(Project.class, req.getProjectId());
        Role role = entityManager.find(Role.class, req.getRoleId());

        entity.setId(employee, project, role);

        entityManager.persist(entity);

        return entity.getId();
    }

    @Transactional
    @Override
    public EmployeeRoleHistory update(EmployeeRoleHistoryRequest req) {
        EmployeeRoleHistory entity = entityManager.find(EmployeeRoleHistory.class,
                                                        new EmployeeRoleHistoryPK(req.getEmployeeId(),
                                                                                  req.getProjectId(),
                                                                                  req.getRoleId()));

        entity.setStartDate(req.getStartDate());
        entity.setEndDate(req.getEndDate());

        return entity;
    }

}
