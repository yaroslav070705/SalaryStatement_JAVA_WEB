package tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import y.cloud.java.App;
import y.cloud.java.dto_models.ProjectRequest;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest(classes = App.class)
public class ProjectDAOTest extends DaoTestSupport {

    @Test
    public void insertProjectDAOTest() {
        ProjectRequest req = new ProjectRequest();
        req.setProjectName("ProjectDAO Insert");
        req.setStartDate(LocalDate.now());
        req.setEndDate(null);

        UUID id = project_dao.insert(req);

        assert Objects.nonNull(project_dao.findById(id));
    }

    @Test
    public void updateProjectDAOTest() {
        ProjectRequest req = new ProjectRequest();
        req.setProjectName("ProjectDAO Update");
        req.setStartDate(LocalDate.now());
        req.setEndDate(null);
        UUID id = project_dao.insert(req);
        req.setProjectName("ProjectDAO Updated");
        req.setProjectId(id);
        req.setStartDate(LocalDate.now());
        req.setEndDate(LocalDate.now().plusDays(30));

        assert Objects.equals(project_dao.update(req).getProjectName(), "ProjectDAO Updated");
    }

    @Test
    public void findAllProjectDAOTest() {
        ProjectRequest req = new ProjectRequest();
        req.setProjectName("ProjectDAO FindAll");
        req.setStartDate(LocalDate.now());
        req.setEndDate(LocalDate.of(2026,5, 4));
        project_dao.insert(req);

        assert !project_dao.findAll().isEmpty();
    }

    @Test
    public void findByParamsProjectDAOTest() {
        ProjectRequest req = new ProjectRequest();
        req.setProjectName("ProjectDAO FindByParams");
        req.setStartDate(LocalDate.now());
        req.setEndDate(LocalDate.of(26,5,4));
        project_dao.insert(req);

        assert !project_dao.findByParams(req).isEmpty();
    }
}
