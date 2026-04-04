package tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import y.cloud.java.App;
import y.cloud.java.dto_models.EmployeeRequest;
import y.cloud.java.dto_models.PostRequest;
import y.cloud.java.salary_statement_models.EmployeePostHistory;
import y.cloud.java.salary_statement_models.EmployeePostHistoryPK;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest(classes = App.class)
public class EmployeeDAOTest extends DaoTestSupport {

    @Test
    public void insertEmployeeDAOTest() {
        PostRequest post_req = new PostRequest();
        post_req.setPostName("EmployeeDAO insert post");
        post_req.setPayoutValue(10000.0);
        UUID post_id = post_dao.insert(post_req);

        EmployeeRequest req = new EmployeeRequest();
        req.setName("Андрей");
        req.setSurname("Иванов");
        req.setMiddleName("Иванович");
        req.setBirthDate(LocalDate.of(1990, 5, 15));
        req.setWorkExperience(5);
        req.setFired(false);
        req.setPostId(post_id);

        UUID emp_id = employee_dao.insert(req);

        EmployeePostHistory eph = employee_dao.findEmployeePostHistoryById(
                new EmployeePostHistoryPK(emp_id, post_id)
        );

        assert Objects.nonNull(emp_id) && Objects.nonNull(eph);
    }

    @Test
    public void updateEmployeeDAOTest() {
        PostRequest post_req = new PostRequest();
        post_req.setPostName("EmployeeDAO update post");
        post_req.setPayoutValue(11000.0);
        UUID post_id = post_dao.insert(post_req);

        EmployeeRequest req = new EmployeeRequest();
        req.setName("Для");
        req.setSurname("Обновления");
        req.setMiddleName("EmployeeDAO");
        req.setBirthDate(LocalDate.of(1990, 5, 15));
        req.setWorkExperience(5);
        req.setFired(false);
        req.setPostId(post_id);
        UUID emp_id = employee_dao.insert(req);
        req.setEmployeeId(emp_id);
        req.setSurname("Обновился");
        req.setWorkExperience(7);

        assert Objects.equals(employee_dao.update(req).getSurname(), "Обновился");
    }

    @Test
    public void findAllEmployeeDAOTest() {
        PostRequest post_req = new PostRequest();
        post_req.setPostName("EmployeeDAO findAll post");
        post_req.setPayoutValue(12000.0);
        UUID post_id = post_dao.insert(post_req);

        EmployeeRequest req = new EmployeeRequest();
        req.setName("FindAll");
        req.setSurname("Employee");
        req.setMiddleName("DAO");
        req.setBirthDate(LocalDate.of(1990, 5, 15));
        req.setWorkExperience(5);
        req.setFired(false);
        req.setPostId(post_id);
        employee_dao.insert(req);

        assert !employee_dao.findAll().isEmpty();
    }
}
