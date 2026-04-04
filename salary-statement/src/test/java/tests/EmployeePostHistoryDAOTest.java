package tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import y.cloud.java.App;
import y.cloud.java.dto_models.EmployeeRequest;
import y.cloud.java.dto_models.EmployeePostHistoryRequest;
import y.cloud.java.dto_models.PostRequest;
import y.cloud.java.salary_statement_models.EmployeePostHistoryPK;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest(classes = App.class)
public class EmployeePostHistoryDAOTest extends DaoTestSupport {

    @Test
    public void insertEmployeePostHistoryDAOTest() {
        PostRequest employee_post_req = new PostRequest();
        employee_post_req.setPostName("EmployeePostHistoryDAO employee post");
        employee_post_req.setPayoutValue(10000.0);
        UUID employee_post_id = post_dao.insert(employee_post_req);

        EmployeeRequest employee_req = new EmployeeRequest();
        employee_req.setName("Insert");
        employee_req.setSurname("EmployeePostHistory");
        employee_req.setMiddleName("DAO");
        employee_req.setBirthDate(LocalDate.of(1990, 5, 15));
        employee_req.setWorkExperience(5);
        employee_req.setFired(false);
        employee_req.setPostId(employee_post_id);
        UUID employee_id = employee_dao.insert(employee_req);

        PostRequest post_req = new PostRequest();
        post_req.setPostName("EmployeePostHistoryDAO post");
        post_req.setPayoutValue(12000.0);
        UUID post_id = post_dao.insert(post_req);

        EmployeePostHistoryRequest req = new EmployeePostHistoryRequest();
        req.setEmployeeId(employee_id);
        req.setPostId(post_id);
        req.setStartDate(LocalDate.now());
        req.setEndDate(null);

        EmployeePostHistoryPK id = employee_post_history_dao.insert(req);

        assert Objects.nonNull(employee_post_history_dao.findById(id));
    }

    @Test
    public void updateEmployeePostHistoryDAOTest() {
        PostRequest employee_post_req = new PostRequest();
        employee_post_req.setPostName("EmployeePostHistoryDAO update employee post");
        employee_post_req.setPayoutValue(13000.0);
        UUID employee_post_id = post_dao.insert(employee_post_req);

        EmployeeRequest employee_req = new EmployeeRequest();
        employee_req.setName("Update");
        employee_req.setSurname("EmployeePostHistory");
        employee_req.setMiddleName("DAO");
        employee_req.setBirthDate(LocalDate.of(1990, 5, 15));
        employee_req.setWorkExperience(5);
        employee_req.setFired(false);
        employee_req.setPostId(employee_post_id);
        UUID employee_id = employee_dao.insert(employee_req);

        PostRequest post_req = new PostRequest();
        post_req.setPostName("EmployeePostHistoryDAO update post");
        post_req.setPayoutValue(14000.0);
        UUID post_id = post_dao.insert(post_req);

        EmployeePostHistoryRequest req = new EmployeePostHistoryRequest();
        req.setEmployeeId(employee_id);
        req.setPostId(post_id);
        req.setStartDate(LocalDate.now());
        req.setEndDate(null);
        employee_post_history_dao.insert(req);

        LocalDate end_date = LocalDate.now().plusDays(1);
        req.setEndDate(end_date);

        assert Objects.equals(employee_post_history_dao.update(req).getEndDate(), end_date);
    }

    @Test
    public void findAllEmployeePostHistoryDAOTest() {
        PostRequest post_req = new PostRequest();
        post_req.setPostName("EmployeePostHistoryDAO findAll employee post");
        post_req.setPayoutValue(15000.0);
        UUID employee_post_id = post_dao.insert(post_req);

        EmployeeRequest req = new EmployeeRequest();
        req.setName("FindAll");
        req.setSurname("EmployeePostHistory");
        req.setMiddleName("DAO");
        req.setBirthDate(LocalDate.of(1990, 5, 15));
        req.setWorkExperience(5);
        req.setFired(false);
        req.setPostId(employee_post_id);
        employee_dao.insert(req);

        assert !employee_post_history_dao.findAll().isEmpty();
    }
}
