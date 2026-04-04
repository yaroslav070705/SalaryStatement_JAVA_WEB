package tests;

import y.cloud.java.App;
import y.cloud.java.dao_models.EmployeeDAO;
import y.cloud.java.dao_models.EmployeePostHistoryDAO;
import y.cloud.java.dao_models.EmployeeRoleHistoryDAO;
import y.cloud.java.dao_models.PostDAO;
import y.cloud.java.dao_models.PayoutDAO;
import y.cloud.java.dao_models.ProjectDAO;
import y.cloud.java.dao_models.ProjectSetupDAO;
import y.cloud.java.dao_models.RoleDAO;
import y.cloud.java.dto_models.EmployeeRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import y.cloud.java.dto_models.EmployeePostHistoryRequest;
import y.cloud.java.dto_models.EmployeeRoleHistoryRequest;
import y.cloud.java.dto_models.PayoutRequest;
import y.cloud.java.dto_models.PostRequest;
import y.cloud.java.dto_models.ProjectRequest;
import y.cloud.java.dto_models.ProjectSetupRequest;
import y.cloud.java.dto_models.RoleRequest;
import y.cloud.java.salary_statement_models.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest(classes = App.class)
public class EmployeeTest {
    @Autowired
    private EmployeeDAO employee_dao;
    @Autowired
    private PostDAO post_dao;
    @Autowired
    private PayoutDAO payout_dao;
    @Autowired
    private ProjectDAO project_dao;
    @Autowired
    private ProjectSetupDAO project_setup_dao;
    @Autowired
    private RoleDAO role_dao;
    @Autowired
    private EmployeeRoleHistoryDAO employee_role_history_dao;
    @Autowired
    private EmployeePostHistoryDAO employee_post_history_dao;

    @Test
    public void findByParamsEmployeeTest() {
        EmployeeRequest req = new EmployeeRequest();
        req.setName("Иван");
        req.setSurname("Иванов");
        req.setMiddleName("Иванович");
        req.setBirthDate(LocalDate.of(1990, 5, 15));
        req.setWorkExperience(5);
        req.setFired(false);
        req.setPostId(post_dao.findByName("Developer").getId());

        assert  !employee_dao.findByParams(req).isEmpty();
    }

    @Test
    public void addEmployeeTest() {
        EmployeeRequest req = new EmployeeRequest();
        req.setName("Андрей");
        req.setSurname("Иванов");
        req.setMiddleName("Иванович");
        req.setBirthDate(LocalDate.of(1990, 5, 15));
        req.setWorkExperience(5);
        req.setFired(false);
        req.setPostId(post_dao.findByName("Developer").getId());

        UUID id = employee_dao.insert(req);

        EmployeePostHistory eph = employee_dao.findEmployeePostHistoryById(new EmployeePostHistoryPK(id, req.getPostId()));
        assert Objects.nonNull(id) && Objects.nonNull(eph);
    }

    @Test
    public void updateEmployeeTest() {
        EmployeeRequest req = new EmployeeRequest();
        req.setName("Для");
        req.setSurname("Обновления");
        req.setMiddleName("Иванович");
        req.setBirthDate(LocalDate.of(1990, 5, 15));
        req.setWorkExperience(5);
        req.setFired(false);
        req.setPostId(post_dao.findByName("Developer").getId());

        UUID emp_id = employee_dao.insert(req);
        req.setEmployeeId(emp_id);
        req.setSurname("Обновился");
        assert Objects.equals(employee_dao.update(req).getSurname(), "Обновился");
    }

    @Test
    public void updateEmployeePostTest() {
        EmployeeRequest req = new EmployeeRequest();
        req.setName("Для");
        req.setSurname("Обновления");
        req.setMiddleName("Должности");
        req.setBirthDate(LocalDate.of(1990, 5, 15));
        req.setWorkExperience(5);
        req.setFired(false);
        req.setPostId(post_dao.findByName("Developer").getId());

        UUID emp_id = employee_dao.insert(req);

        PostRequest p_req = new PostRequest();
        p_req.setPostName("Waiter");
        p_req.setPayoutValue((double)15000);
        UUID post_id = post_dao.insert(p_req);
        System.out.print(post_id);

        Employee emp = employee_dao.findById(emp_id);
        Post post = post_dao.findById(post_id);
        employee_dao.updatePost(emp_id, post_id);

        emp = employee_dao.findById(emp_id);
        assert emp.getPostId().equals(post_id)
                && Objects.nonNull(employee_dao.findEmployeePostHistoryById(new EmployeePostHistoryPK(emp_id, post_id)));
    }

    @Test
    public void skipUpdatePost() {
        EmployeeRequest req = new EmployeeRequest();
        req.setName("Без");
        req.setSurname("Смены");
        req.setMiddleName("Должности");
        req.setBirthDate(LocalDate.of(1990, 5, 15));
        req.setWorkExperience(5);
        req.setFired(false);
        UUID post_id = post_dao.findByName("Developer").getId();
        req.setPostId(post_id);

        UUID emp_id = employee_dao.insert(req);
        employee_dao.updatePost(emp_id, post_id);
        Employee emp = employee_dao.findById(emp_id);

        assert Objects.equals(emp.getPostId(), post_id);
    }

    @Test
    public void fireEmployeeTest() {
        EmployeeRequest req = new EmployeeRequest();
        req.setName("Будет");
        req.setSurname("Уволен");
        req.setMiddleName("Иванович");
        req.setBirthDate(LocalDate.of(1990, 5, 15));
        req.setWorkExperience(5);
        req.setFired(false);
        req.setPostId(post_dao.findByName("Developer").getId());

        UUID emp_id = employee_dao.insert(req);
        employee_dao.fire(emp_id);
        Employee emp = employee_dao.findById(emp_id);

        assert emp.getFired();
    }

    @Test
    public void findPayoutsTest() {
        EmployeeRequest req = new EmployeeRequest();
        req.setName("Получит");
        req.setSurname("Премию");
        req.setMiddleName("Иванович");
        req.setBirthDate(LocalDate.of(1990, 5, 15));
        req.setWorkExperience(5);
        req.setFired(false);
        req.setPostId(post_dao.findByName("Developer").getId());

        UUID emp_id = employee_dao.insert(req);

        PayoutRequest p_req = new PayoutRequest();
        p_req.setEmployeeId(emp_id);
        p_req.setPayoutTypeId(UUID.fromString("e9b5493a-3b62-4bc4-83db-448022e9e0d4"));
        p_req.setDate(LocalDate.now());
        p_req.setValue((double)10000);
        PayoutPK payout_id = payout_dao.insert(p_req);

        assert !employee_dao.findAllPayouts(emp_id).isEmpty();
    }

    @Test
    public void findEmployeeRoleTest() {
        EmployeeRequest emp_req = new EmployeeRequest();
        emp_req.setName("Ищет");
        emp_req.setSurname("Роль");
        emp_req.setMiddleName("ВПроекте");
        emp_req.setBirthDate(LocalDate.of(1990, 5, 15));
        emp_req.setWorkExperience(5);
        emp_req.setFired(false);
        emp_req.setPostId(post_dao.findByName("Developer").getId());

        UUID emp_id = employee_dao.insert(emp_req);

        ProjectRequest project_req = new ProjectRequest();
        project_req.setProjectName("Find employee role test project");
        project_req.setStartDate(LocalDate.now());
        project_req.setEndDate(null);
        UUID project_id = project_dao.insert(project_req);

        RoleRequest role_req = new RoleRequest();
        role_req.setRoleName("Find employee role test role");
        UUID role_id = role_dao.insert(role_req);

        EmployeeRoleHistoryRequest history_req = new EmployeeRoleHistoryRequest();
        history_req.setEmployeeId(emp_id);
        history_req.setProjectId(project_id);
        history_req.setRoleId(role_id);
        history_req.setStartDate(LocalDate.now());
        history_req.setEndDate(null);
        employee_role_history_dao.insert(history_req);

        assert !employee_dao.findAllEmployeeRoleHistory(emp_id).isEmpty();
    }

    @Test
    public void findEmployeePostTest() {
        EmployeeRequest emp_req = new EmployeeRequest();
        emp_req.setName("Ищет");
        emp_req.setSurname("Должность");
        emp_req.setMiddleName("ВИстории");
        emp_req.setBirthDate(LocalDate.of(1990, 5, 15));
        emp_req.setWorkExperience(5);
        emp_req.setFired(false);
        emp_req.setPostId(post_dao.findByName("Developer").getId());

        UUID emp_id = employee_dao.insert(emp_req);

        PostRequest post_req = new PostRequest();
        post_req.setPostName("Find employee post test post");
        post_req.setPayoutValue((double) 20000);
        UUID post_id = post_dao.insert(post_req);

        EmployeePostHistoryRequest history_req = new EmployeePostHistoryRequest();
        history_req.setEmployeeId(emp_id);
        history_req.setPostId(post_id);
        history_req.setStartDate(LocalDate.now());
        history_req.setEndDate(null);
        employee_post_history_dao.insert(history_req);

        assert !employee_dao.findAllEmployeePostHistory(emp_id).isEmpty();
    }

    @Test
    public void findBonusesTest() {
        EmployeeRequest req = new EmployeeRequest();
        req.setName("Получит");
        req.setSurname("Бонус");
        req.setMiddleName("Иванович");
        req.setBirthDate(LocalDate.of(1990, 5, 15));
        req.setWorkExperience(5);
        req.setFired(false);
        req.setPostId(post_dao.findByName("Developer").getId());

        UUID emp_id = employee_dao.insert(req);

        PayoutRequest p_req = new PayoutRequest();
        p_req.setEmployeeId(emp_id);
        p_req.setPayoutTypeId(UUID.fromString("e9b5493a-3b62-4bc4-83db-448022e9e0d4"));
        p_req.setDate(LocalDate.now());
        p_req.setValue((double) 10000);
        payout_dao.insert(p_req);

        assert !employee_dao.findAllEmployeeBonus(emp_id).isEmpty();
    }

    @Test
    public void findAllEmployeeByProjectTest() {
        EmployeeRequest emp_req = new EmployeeRequest();
        emp_req.setName("Ищет");
        emp_req.setSurname("Проект");
        emp_req.setMiddleName("ПоСотруднику");
        emp_req.setBirthDate(LocalDate.of(1990, 5, 15));
        emp_req.setWorkExperience(5);
        emp_req.setFired(false);
        emp_req.setPostId(post_dao.findByName("Developer").getId());
        UUID emp_id = employee_dao.insert(emp_req);

        ProjectRequest project_req = new ProjectRequest();
        project_req.setProjectName("Find all employee by project test");
        project_req.setStartDate(LocalDate.now());
        project_req.setEndDate(null);
        UUID project_id = project_dao.insert(project_req);

        RoleRequest role_req = new RoleRequest();
        role_req.setRoleName("Find all employee by project role");
        UUID role_id = role_dao.insert(role_req);

        ProjectSetupRequest project_setup_req = new ProjectSetupRequest();
        project_setup_req.setEmployeeId(emp_id);
        project_setup_req.setProjectId(project_id);
        project_setup_req.setRoleId(role_id);
        project_setup_dao.insert(project_setup_req);

        assert !employee_dao.findAllEmployeeByProject(project_id).isEmpty();
    }
}
