package tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import y.cloud.java.App;
import y.cloud.java.dto_models.ProjectRequest;
import y.cloud.java.dto_models.RolePayoutValueRequest;
import y.cloud.java.dto_models.RoleRequest;
import y.cloud.java.salary_statement_models.RolePayoutValuePK;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest(classes = App.class)
public class RolePayoutValueDAOTest extends DaoTestSupport {

    @Test
    public void insertRolePayoutValueDAOTest() {
        ProjectRequest project_req = new ProjectRequest();
        project_req.setProjectName("RolePayoutValueDAO project");
        project_req.setStartDate(LocalDate.now());
        project_req.setEndDate(null);
        UUID project_id = project_dao.insert(project_req);

        RoleRequest role_req = new RoleRequest();
        role_req.setRoleName("RolePayoutValueDAO role");
        UUID role_id = role_dao.insert(role_req);

        RolePayoutValueRequest req = new RolePayoutValueRequest();
        req.setProjectId(project_id);
        req.setRoleId(role_id);
        req.setValue(20000.0);

        RolePayoutValuePK id = role_payout_value_dao.insert(req);

        assert Objects.nonNull(role_payout_value_dao.findById(id));
    }

    @Test
    public void updateRolePayoutValueDAOTest() {
        ProjectRequest project_req = new ProjectRequest();
        project_req.setProjectName("RolePayoutValueDAO update project");
        project_req.setStartDate(LocalDate.now());
        project_req.setEndDate(null);
        UUID project_id = project_dao.insert(project_req);

        RoleRequest role_req = new RoleRequest();
        role_req.setRoleName("RolePayoutValueDAO update role");
        UUID role_id = role_dao.insert(role_req);

        RolePayoutValueRequest req = new RolePayoutValueRequest();
        req.setProjectId(project_id);
        req.setRoleId(role_id);
        req.setValue(20000.0);
        role_payout_value_dao.insert(req);

        req.setValue(25000.0);

        assert Objects.equals(role_payout_value_dao.update(req).getValue(), 25000.0);
    }

    @Test
    public void findAllRolePayoutValueDAOTest() {
        ProjectRequest project_req = new ProjectRequest();
        project_req.setProjectName("RolePayoutValueDAO findAll project");
        project_req.setStartDate(LocalDate.now());
        project_req.setEndDate(null);
        UUID project_id = project_dao.insert(project_req);

        RoleRequest role_req = new RoleRequest();
        role_req.setRoleName("RolePayoutValueDAO findAll role");
        UUID role_id = role_dao.insert(role_req);

        RolePayoutValueRequest req = new RolePayoutValueRequest();
        req.setProjectId(project_id);
        req.setRoleId(role_id);
        req.setValue(22000.0);
        role_payout_value_dao.insert(req);

        assert !role_payout_value_dao.findAll().isEmpty();
    }

    @Test
    public void findByParamsRolePayoutValueDAOTest() {
        ProjectRequest project_req = new ProjectRequest();
        project_req.setProjectName("RolePayoutValueDAO findByParams project");
        project_req.setStartDate(LocalDate.now());
        project_req.setEndDate(null);
        UUID project_id = project_dao.insert(project_req);

        RoleRequest role_req = new RoleRequest();
        role_req.setRoleName("RolePayoutValueDAO findByParams role");
        UUID role_id = role_dao.insert(role_req);

        RolePayoutValueRequest req = new RolePayoutValueRequest();
        req.setProjectId(project_id);
        req.setRoleId(role_id);
        req.setValue(23000.0);
        role_payout_value_dao.insert(req);

        assert !role_payout_value_dao.findByParams(req).isEmpty();
    }
}
