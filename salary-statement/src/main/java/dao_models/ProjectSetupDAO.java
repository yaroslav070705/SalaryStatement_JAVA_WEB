package dao_models;

import dto_models.ProjectRequest;
import dto_models.ProjectSetupRequest;
import org.springframework.transaction.annotation.Transactional;
import salary_statement_models.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ProjectSetupDAO implements ProjectSetupInterfaceDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ProjectSetup findById(ProjectSetupPK id) {
        return entityManager.find(ProjectSetup.class, id);
    }

    @Override
    public List<ProjectSetup> findAll() {
        return entityManager.createQuery("SELECT ps FROM ProjectSetup ps", ProjectSetup.class).getResultList();
    }

    @Transactional
    @Override
    public void insert(ProjectSetupRequest project_setup_req) {
        Employee employee = entityManager.find(Employee.class, project_setup_req.getEmployeeId());
        Project project = entityManager.find(Project.class, project_setup_req.getProjectId());
        Role role = entityManager.find(Role.class, project_setup_req.getRoleId());

        ProjectSetup project_setup = new ProjectSetup();
        project_setup.setId(employee, project);
        project_setup.setRoleId(role);
        entityManager.persist(project_setup);

    }

    @Transactional
    @Override
    public void update(ProjectSetupRequest project_setup_req) {
        ProjectSetup project_setup = entityManager.find(ProjectSetup.class, new ProjectSetupPK(project_setup_req.getEmployeeId(),
                                                                                               project_setup_req.getProjectId()));

        Role role_id = entityManager.find(Role.class, project_setup_req.getRoleId());
        project_setup.setRoleId(role_id);
    }

    @Transactional
    @Override
    public void delete(ProjectSetupPK id) {
        ProjectSetup project_setup = entityManager.find(ProjectSetup.class, id);
        entityManager.remove(project_setup);
    }
}
