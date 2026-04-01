package y.cloud.java.dao_models;

import y.cloud.java.dto_models.RoleRequest;
import y.cloud.java.salary_statement_models.Role;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class RoleDAO implements RoleInterfaceDAO{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Role findById(UUID id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Transactional
    @Override
    public UUID insert(RoleRequest req) {
       Role role = new Role(req);

       entityManager.persist(role);

       return role.getId();
    }

    @Transactional
    @Override
    public void update(RoleRequest role_req) {
        Role role = entityManager.find(Role.class, role_req.getRoleId());

        role.setRoleName(role_req.getRoleName());
    }
}
