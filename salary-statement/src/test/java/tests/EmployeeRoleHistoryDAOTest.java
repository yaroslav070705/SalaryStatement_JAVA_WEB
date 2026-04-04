package tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import y.cloud.java.App;
import y.cloud.java.dto_models.EmployeeRequest;
import y.cloud.java.dto_models.EmployeeRoleHistoryRequest;
import y.cloud.java.dto_models.PostRequest;
import y.cloud.java.dto_models.ProjectRequest;
import y.cloud.java.dto_models.RoleRequest;
import y.cloud.java.salary_statement_models.EmployeeRoleHistoryPK;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest(classes = App.class)
public class EmployeeRoleHistoryDAOTest extends DaoTestSupport {

    @Test
    public void insertEmployeeRoleHistoryDAOTest() {
        PostRequest post_req = new PostRequest();
        post_req.setPostName("EmployeeRoleHistoryDAO post");
        post_req.setPayoutValue(10000.0);
        UUID post_id = post_dao.insert(post_req);

        EmployeeRequest employee_req = new EmployeeRequest();
        employee_req.setName("Insert");
        employee_req.setSurname("EmployeeRoleHistory");
        employee_req.setMiddleName("DAO");
        employee_req.setBirthDate(LocalDate.of(1990, 5, 15));
        employee_req.setWorkExperience(5);
        employee_req.setFired(false);
        employee_req.setPostId(post_id);
        UUID employee_id = employee_dao.insert(employee_req);

        ProjectRequest project_req = new ProjectRequest();
        project_req.setProjectName("EmployeeRoleHistoryDAO project");
        project_req.setStartDate(LocalDate.now());
        project_req.setEndDate(null);
        UUID project_id = project_dao.insert(project_req);

        RoleRequest role_req = new RoleRequest();
        role_req.setRoleName("EmployeeRoleHistoryDAO role");
        UUID role_id = role_dao.insert(role_req);

        EmployeeRoleHistoryRequest req = new EmployeeRoleHistoryRequest();
        req.setEmployeeId(employee_id);
        req.setProjectId(project_id);
        req.setRoleId(role_id);
        req.setStartDate(LocalDate.now());
        req.setEndDate(null);

        EmployeeRoleHistoryPK id = employee_role_history_dao.insert(req);

        assert Objects.nonNull(employee_role_history_dao.findById(id));
    }

    @Test
    public void updateEmployeeRoleHistoryDAOTest() {
        PostRequest post_req = new PostRequest();
        post_req.setPostName("EmployeeRoleHistoryDAO update post");
        post_req.setPayoutValue(11000.0);
        UUID post_id = post_dao.insert(post_req);

        EmployeeRequest employee_req = new EmployeeRequest();
        employee_req.setName("Update");
        employee_req.setSurname("EmployeeRoleHistory");
        employee_req.setMiddleName("DAO");
        employee_req.setBirthDate(LocalDate.of(1990, 5, 15));
        employee_req.setWorkExperience(5);
        employee_req.setFired(false);
        employee_req.setPostId(post_id);
        UUID employee_id = employee_dao.insert(employee_req);

        ProjectRequest project_req = new ProjectRequest();
        project_req.setProjectName("EmployeeRoleHistoryDAO update project");
        project_req.setStartDate(LocalDate.now());
        project_req.setEndDate(null);
        UUID project_id = project_dao.insert(project_req);

        RoleRequest role_req = new RoleRequest();
        role_req.setRoleName("EmployeeRoleHistoryDAO update role");
        UUID role_id = role_dao.insert(role_req);

        EmployeeRoleHistoryRequest req = new EmployeeRoleHistoryRequest();
        req.setEmployeeId(employee_id);
        req.setProjectId(project_id);
        req.setRoleId(role_id);
        req.setStartDate(LocalDate.now());
        req.setEndDate(null);
        employee_role_history_dao.insert(req);

        LocalDate end_date = LocalDate.now().plusDays(2);
        req.setEndDate(end_date);

        assert Objects.equals(employee_role_history_dao.update(req).getEndDate(), end_date);
    }

    @Test
    public void findAllEmployeeRoleHistoryDAOTest() {
        PostRequest post_req = new PostRequest();
        post_req.setPostName("EmployeeRoleHistoryDAO findAll post");
        post_req.setPayoutValue(12000.0);
        UUID post_id = post_dao.insert(post_req);

        EmployeeRequest employee_req = new EmployeeRequest();
        employee_req.setName("FindAll");
        employee_req.setSurname("EmployeeRoleHistory");
        employee_req.setMiddleName("DAO");
        employee_req.setBirthDate(LocalDate.of(1990, 5, 15));
        employee_req.setWorkExperience(5);
        employee_req.setFired(false);
        employee_req.setPostId(post_id);
        UUID employee_id = employee_dao.insert(employee_req);

        ProjectRequest project_req = new ProjectRequest();
        project_req.setProjectName("EmployeeRoleHistoryDAO findAll project");
        project_req.setStartDate(LocalDate.now());
        project_req.setEndDate(null);
        UUID project_id = project_dao.insert(project_req);

        RoleRequest role_req = new RoleRequest();
        role_req.setRoleName("EmployeeRoleHistoryDAO findAll role");
        UUID role_id = role_dao.insert(role_req);

        EmployeeRoleHistoryRequest req = new EmployeeRoleHistoryRequest();
        req.setEmployeeId(employee_id);
        req.setProjectId(project_id);
        req.setRoleId(role_id);
        req.setStartDate(LocalDate.now());
        req.setEndDate(null);
        employee_role_history_dao.insert(req);

        assert !employee_role_history_dao.findAll().isEmpty();
    }
}
