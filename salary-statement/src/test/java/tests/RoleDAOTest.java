package tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import y.cloud.java.App;
import y.cloud.java.dto_models.RoleRequest;

import java.util.Objects;
import java.util.UUID;

@SpringBootTest(classes = App.class)
public class RoleDAOTest extends DaoTestSupport {

    @Test
    public void insertRoleDAOTest() {
        RoleRequest req = new RoleRequest();
        req.setRoleName("RoleDAO Insert");

        UUID id = role_dao.insert(req);

        assert Objects.nonNull(role_dao.findById(id));
    }

    @Test
    public void updateRoleDAOTest() {
        RoleRequest req = new RoleRequest();
        req.setRoleName("RoleDAO Update");
        UUID id = role_dao.insert(req);
        req.setRoleName("RoleDAO Updated");
        req.setRoleId(id);

        assert Objects.equals(role_dao.update(req).getRoleName(), "RoleDAO Updated");
    }

    @Test
    public void findAllRoleDAOTest() {
        RoleRequest req = new RoleRequest();
        req.setRoleName("RoleDAO FindAll");
        role_dao.insert(req);

        assert !role_dao.findAll().isEmpty();
    }
}
