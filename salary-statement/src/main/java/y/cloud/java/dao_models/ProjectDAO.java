package y.cloud.java.dao_models;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import y.cloud.java.dto_models.ProjectRequest;
import y.cloud.java.models_utils.NotStated;
import y.cloud.java.salary_statement_models.Project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class ProjectDAO implements ProjectInterfaceDAO {

    @PersistenceContext
    EntityManager entityManager;

    private CriteriaBuilder cb;

    @PostConstruct
    public void init() {
        cb = entityManager.getCriteriaBuilder();
    }

    @Override
    public Project findById(UUID project_id){
        return entityManager.find(Project.class, project_id);
    }

    @Override
    public List<Project> findAll() {
        return entityManager.createQuery("SELECT p from Project p", Project.class).getResultList();
    }

    @Override
    public List<Project> findByParams(ProjectRequest req) {
        CriteriaQuery<Project> query = cb.createQuery(Project.class);
        Root<Project> root = query.from(Project.class);
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(req.getProjectName()) && !req.getProjectName().isEmpty()) {
            predicates.add(cb.equal(root.get("project_name"), req.getProjectName()));
        }
        if (!Objects.equals(req.getStartDate(), NotStated.REL.value())) {
            predicates.add(cb.equal(root.get("start_date"), req.getStartDate()));
        }
        if (!Objects.equals(req.getEndDate(), NotStated.REL.value())) {
            predicates.add(cb.equal(root.get("end_date"), req.getEndDate()));
        }

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    @Override
    public UUID insert(ProjectRequest project_req){
        Project project = new Project(project_req);

        entityManager.persist(project);

        return project.getId();
    }

    @Transactional
    @Override
    public Project update(ProjectRequest project_req){
        Project project = entityManager.find(Project.class, project_req.getProjectId());

        project.setStartDate(project_req.getStartDate());
        project.setProjectName(project_req.getProjectName());
        project.setEndDate(project_req.getEndDate());

        return project;
    }

}
