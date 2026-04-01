package dao_models;

import dto_models.EmployeeRoleHistoryRequest;
import salary_statement_models.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EmployeeRoleHistoryDAO implements EmployeeRoleHistoryInterfaceDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EmployeeRoleHistory findById(EmployeeRoleHistoryPK id) {
        return entityManager.find(EmployeeRoleHistory.class, id);
    }

    @Override
    public List<EmployeeRoleHistory> findAll() {
        return entityManager.createQuery("SELECT erh FROM EmployeeRoleHistory erh", EmployeeRoleHistory.class).getResultList();
    }

    @Transactional
    @Override
    public EmployeePostHistoryPK insert(EmployeeRoleHistoryRequest req) {
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
    public void update(EmployeeRoleHistoryRequest req) {
        EmployeeRoleHistory entity = entityManager.find(EmployeeRoleHistory.class,
                                                        new EmployeeRoleHistoryPK(req.getEmployeeId(),
                                                                                  req.getProjectId(),
                                                                                  req.getRoleId()));

        entity.setStartDate(req.getStartDate());
        entity.setEndDate(req.getEndDate());
    }

    @Transactional
    @Override
    public void delete(EmployeeRoleHistoryPK pk) {
    }
}