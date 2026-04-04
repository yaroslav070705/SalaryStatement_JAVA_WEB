package tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import y.cloud.java.App;
import y.cloud.java.dto_models.EmployeeRequest;
import y.cloud.java.dto_models.PostRequest;
import y.cloud.java.dto_models.ProjectRequest;
import y.cloud.java.dto_models.ProjectSetupRequest;
import y.cloud.java.dto_models.RoleRequest;
import y.cloud.java.salary_statement_models.ProjectSetupPK;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest(classes = App.class)
public class ProjectSetupDAOTest extends DaoTestSupport {

    @Test
    public void insertProjectSetupDAOTest() {
        PostRequest post_req = new PostRequest();
        post_req.setPostName("ProjectSetupDAO post");
        post_req.setPayoutValue(10000.0);
        UUID post_id = post_dao.insert(post_req);

        EmployeeRequest employee_req = new EmployeeRequest();
        employee_req.setName("Insert");
        employee_req.setSurname("ProjectSetup");
        employee_req.setMiddleName("DAO");
        employee_req.setBirthDate(LocalDate.of(1990, 5, 15));
        employee_req.setWorkExperience(5);
        employee_req.setFired(false);
        employee_req.setPostId(post_id);
        UUID employee_id = employee_dao.insert(employee_req);

        ProjectRequest project_req = new ProjectRequest();
        project_req.setProjectName("ProjectSetupDAO project");
        project_req.setStartDate(LocalDate.now());
        project_req.setEndDate(null);
        UUID project_id = project_dao.insert(project_req);

        RoleRequest role_req = new RoleRequest();
        role_req.setRoleName("ProjectSetupDAO role");
        UUID role_id = role_dao.insert(role_req);

        ProjectSetupRequest req = new ProjectSetupRequest();
        req.setEmployeeId(employee_id);
        req.setProjectId(project_id);
        req.setRoleId(role_id);

        ProjectSetupPK id = project_setup_dao.insert(req);

        assert Objects.nonNull(project_setup_dao.findById(id));
    }

    @Test
    public void updateProjectSetupDAOTest() {
        PostRequest post_req = new PostRequest();
        post_req.setPostName("ProjectSetupDAO update post");
        post_req.setPayoutValue(11000.0);
        UUID post_id = post_dao.insert(post_req);

        EmployeeRequest employee_req = new EmployeeRequest();
        employee_req.setName("Update");
        employee_req.setSurname("ProjectSetup");
        employee_req.setMiddleName("DAO");
        employee_req.setBirthDate(LocalDate.of(1990, 5, 15));
        employee_req.setWorkExperience(5);
        employee_req.setFired(false);
        employee_req.setPostId(post_id);
        UUID employee_id = employee_dao.insert(employee_req);

        ProjectRequest project_req = new ProjectRequest();
        project_req.setProjectName("ProjectSetupDAO update project");
        project_req.setStartDate(LocalDate.now());
        project_req.setEndDate(null);
        UUID project_id = project_dao.insert(project_req);

        RoleRequest role_req = new RoleRequest();
        role_req.setRoleName("ProjectSetupDAO update role");
        UUID role_id = role_dao.insert(role_req);

        RoleRequest new_role_req = new RoleRequest();
        new_role_req.setRoleName("ProjectSetupDAO new role");
        UUID new_role_id = role_dao.insert(new_role_req);

        ProjectSetupRequest req = new ProjectSetupRequest();
        req.setEmployeeId(employee_id);
        req.setProjectId(project_id);
        req.setRoleId(role_id);
        project_setup_dao.insert(req);

        req.setRoleId(new_role_id);

        assert Objects.equals(project_setup_dao.update(req).getRoleId(), new_role_id);
    }

    @Test
    public void findAllProjectSetupDAOTest() {
        PostRequest post_req = new PostRequest();
        post_req.setPostName("ProjectSetupDAO findAll post");
        post_req.setPayoutValue(12000.0);
        UUID post_id = post_dao.insert(post_req);

        EmployeeRequest employee_req = new EmployeeRequest();
        employee_req.setName("FindAll");
        employee_req.setSurname("ProjectSetup");
        employee_req.setMiddleName("DAO");
        employee_req.setBirthDate(LocalDate.of(1990, 5, 15));
        employee_req.setWorkExperience(5);
        employee_req.setFired(false);
        employee_req.setPostId(post_id);
        UUID employee_id = employee_dao.insert(employee_req);

        ProjectRequest project_req = new ProjectRequest();
        project_req.setProjectName("ProjectSetupDAO findAll project");
        project_req.setStartDate(LocalDate.now());
        project_req.setEndDate(null);
        UUID project_id = project_dao.insert(project_req);

        RoleRequest role_req = new RoleRequest();
        role_req.setRoleName("ProjectSetupDAO findAll role");
        UUID role_id = role_dao.insert(role_req);

        ProjectSetupRequest req = new ProjectSetupRequest();
        req.setEmployeeId(employee_id);
        req.setProjectId(project_id);
        req.setRoleId(role_id);
        project_setup_dao.insert(req);

        assert !project_setup_dao.findAll().isEmpty();
    }
}
