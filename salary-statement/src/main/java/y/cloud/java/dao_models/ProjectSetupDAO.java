package y.cloud.java.dao_models;

import y.cloud.java.dto_models.ProjectSetupRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

import org.springframework.stereotype.Repository;
import y.cloud.java.models_utils.NotStated;
import y.cloud.java.salary_statement_models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ProjectSetupDAO implements ProjectSetupInterfaceDAO {

    @PersistenceContext
    EntityManager entityManager;

    private CriteriaBuilder cb;

    @PostConstruct
    public void init() {
        cb = entityManager.getCriteriaBuilder();
    }

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
    public List<ProjectSetup> findByParams(ProjectSetupRequest req) {
        CriteriaQuery<ProjectSetup> query = cb.createQuery(ProjectSetup.class);
        Root<ProjectSetup> root = query.from(ProjectSetup.class);
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

    @Override
    @Transactional
    public List<Employee> getProjectEmployees(UUID project_id) {
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<ProjectSetup> root = query.from(ProjectSetup.class);
        Join<ProjectSetup, Employee> e = root.join("employee_id");

        query.select(e).distinct(true);

        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    @Override
    public ProjectSetupPK insert(ProjectSetupRequest project_setup_req) {
        Employee employee = entityManager.find(Employee.class, project_setup_req.getEmployeeId());
        Project project = entityManager.find(Project.class, project_setup_req.getProjectId());
        Role role = entityManager.find(Role.class, project_setup_req.getRoleId());

        ProjectSetup project_setup = new ProjectSetup();
        project_setup.setId(employee, project);
        project_setup.setRoleId(role);
        entityManager.persist(project_setup);

        return project_setup.getId();

    }

    @Transactional
    @Override
    public ProjectSetup update(ProjectSetupRequest project_setup_req) {
        ProjectSetup project_setup = entityManager.find(ProjectSetup.class, new ProjectSetupPK(project_setup_req.getEmployeeId(),
                                                                                               project_setup_req.getProjectId()));

        Role role_id = entityManager.find(Role.class, project_setup_req.getRoleId());
        project_setup.setRoleId(role_id);

        return project_setup;
    }

}
