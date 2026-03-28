package dao_models;

import dto_models.RolePayoutValueRequest;

import org.springframework.stereotype.Repository;
import salary_statement_models.Project;
import salary_statement_models.Role;
import salary_statement_models.RolePayoutValue;
import salary_statement_models.RolePayoutValuePK;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RolePayoutValueDAO implements RolePayoutValueInterfaceDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public RolePayoutValue findById(RolePayoutValuePK id) {
        return entityManager.find(RolePayoutValue.class, id);
    }

    @Transactional
    @Override
    public List<RolePayoutValue> findAll() {
        return entityManager.createQuery("SELECT rpv FROM RolePayoutValue rpv", RolePayoutValue.class).getResultList();
    }

    @Transactional
    @Override
    public void insert(RolePayoutValueRequest req) {
        RolePayoutValue entity = new RolePayoutValue(req);

        Project project = entityManager.find(Project.class, req.getProjectId());
        Role role = entityManager.find(Role.class, req.getRoleId());

        entity.setId(project, role);

        entityManager.persist(entity);
    }

    @Transactional
    @Override
    public void update(RolePayoutValueRequest req) {
        RolePayoutValue entity = findById(new RolePayoutValuePK(req.getProjectId(), req.getRoleId()));

        entity.setValue(req.getValue());
    }

    @Transactional
    @Override
    public void delete(RolePayoutValuePK id) {
    }
}
