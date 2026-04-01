package y.cloud.java.dao_models;

import y.cloud.java.dto_models.ProjectRequest;
import y.cloud.java.salary_statement_models.Project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class ProjectDAO implements ProjectInterfaceDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Project findById(UUID project_id){
        return entityManager.find(Project.class, project_id);
    }

    @Override
    public List<Project> findAll() {
        return entityManager.createQuery("SELECT p from Project", Project.class).getResultList();
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
    public void update(ProjectRequest project_req){
        Project project = entityManager.find(Project.class, project_req.getProjectId());

        project.setStartDate(project_req.getStartDate());
        project.setProjectName(project_req.getProjectName());
        project.setEndDate(project_req.getEndDate());
    }

    @Transactional
    @Override
    public void delete(UUID project_id){
        Project project = entityManager.find(Project.class, project_id);

        entityManager.remove(project);
    }
}
