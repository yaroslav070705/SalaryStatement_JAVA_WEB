package y.cloud.java.dao_models;

import y.cloud.java.dto_models.RolePayoutValueRequest;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import y.cloud.java.salary_statement_models.Project;
import y.cloud.java.salary_statement_models.Role;
import y.cloud.java.salary_statement_models.RolePayoutValue;
import y.cloud.java.salary_statement_models.RolePayoutValuePK;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RolePayoutValueDAO implements RolePayoutValueInterfaceDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaBuilder cb;

    @PostConstruct
    public void init() {
        cb = entityManager.getCriteriaBuilder();
    }

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
    public List<RolePayoutValue> findByParams(RolePayoutValueRequest req) {
        CriteriaQuery<RolePayoutValue> query = cb.createQuery(RolePayoutValue.class);
        Root<RolePayoutValue> root = query.from(RolePayoutValue.class);
        List<Predicate> predicates = new ArrayList<>();

        if(req.getProjectId() != null) {
            predicates.add(cb.equal(root.get("project_id").get("project_id"), req.getProjectId()));
        }
        if(req.getRoleId() != null) {
            predicates.add(cb.equal(root.get("role_id").get("role_id"), req.getRoleId()));
        }

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    @Override
    public RolePayoutValuePK insert(RolePayoutValueRequest req) {
        RolePayoutValue entity = new RolePayoutValue(req);

        Project project = entityManager.find(Project.class, req.getProjectId());
        Role role = entityManager.find(Role.class, req.getRoleId());

        entity.setId(project, role);

        entityManager.persist(entity);

        return entity.getId();
    }

    @Transactional
    @Override
    public RolePayoutValue update(RolePayoutValueRequest req) {
        RolePayoutValue entity = findById(new RolePayoutValuePK(req.getProjectId(), req.getRoleId()));

        entity.setValue(req.getValue());

        return entity;
    }

}
