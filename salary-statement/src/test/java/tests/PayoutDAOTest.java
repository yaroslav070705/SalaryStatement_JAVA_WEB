package tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import y.cloud.java.App;
import y.cloud.java.dto_models.EmployeeRequest;
import y.cloud.java.dto_models.PayoutRequest;
import y.cloud.java.dto_models.PayoutTypeRequest;
import y.cloud.java.dto_models.PostRequest;
import y.cloud.java.salary_statement_models.PayoutPK;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest(classes = App.class)
public class PayoutDAOTest extends DaoTestSupport {

    @Test
    public void insertPayoutDAOTest() {
        PostRequest post_req = new PostRequest();
        post_req.setPostName("PayoutDAO insert post");
        post_req.setPayoutValue(10000.0);
        UUID post_id = post_dao.insert(post_req);

        EmployeeRequest employee_req = new EmployeeRequest();
        employee_req.setName("Insert");
        employee_req.setSurname("Payout");
        employee_req.setMiddleName("DAO");
        employee_req.setBirthDate(LocalDate.of(1990, 5, 15));
        employee_req.setWorkExperience(5);
        employee_req.setFired(false);
        employee_req.setPostId(post_id);
        UUID employee_id = employee_dao.insert(employee_req);

        PayoutTypeRequest payout_type_req = new PayoutTypeRequest();
        payout_type_req.setPayoutType("PayoutDAO Insert Bonus");
        UUID payout_type_id = payout_type_dao.insert(payout_type_req);

        PayoutRequest req = new PayoutRequest();
        req.setEmployeeId(employee_id);
        req.setPayoutTypeId(payout_type_id);
        req.setDate(LocalDate.now());
        req.setValue(5000.0);

        PayoutPK id = payout_dao.insert(req);

        assert Objects.nonNull(payout_dao.findById(id));
    }

    @Test
    public void updatePayoutDAOTest() {
        PostRequest post_req = new PostRequest();
        post_req.setPostName("PayoutDAO update post");
        post_req.setPayoutValue(11000.0);
        UUID post_id = post_dao.insert(post_req);

        EmployeeRequest employee_req = new EmployeeRequest();
        employee_req.setName("Update");
        employee_req.setSurname("Payout");
        employee_req.setMiddleName("DAO");
        employee_req.setBirthDate(LocalDate.of(1990, 5, 15));
        employee_req.setWorkExperience(5);
        employee_req.setFired(false);
        employee_req.setPostId(post_id);
        UUID employee_id = employee_dao.insert(employee_req);

        PayoutTypeRequest payout_type_req = new PayoutTypeRequest();
        payout_type_req.setPayoutType("PayoutDAO Update Bonus");
        UUID payout_type_id = payout_type_dao.insert(payout_type_req);

        PayoutRequest req = new PayoutRequest();
        req.setEmployeeId(employee_id);
        req.setPayoutTypeId(payout_type_id);
        req.setDate(LocalDate.now());
        req.setValue(5000.0);
        payout_dao.insert(req);

        req.setValue(7000.0);

        assert Objects.equals(payout_dao.update(req).getValue(), 7000.0);
    }

    @Test
    public void findAllPayoutDAOTest() {
        PostRequest post_req = new PostRequest();
        post_req.setPostName("PayoutDAO findAll post");
        post_req.setPayoutValue(12000.0);
        UUID post_id = post_dao.insert(post_req);

        EmployeeRequest employee_req = new EmployeeRequest();
        employee_req.setName("FindAll");
        employee_req.setSurname("Payout");
        employee_req.setMiddleName("DAO");
        employee_req.setBirthDate(LocalDate.of(1990, 5, 15));
        employee_req.setWorkExperience(5);
        employee_req.setFired(false);
        employee_req.setPostId(post_id);
        UUID employee_id = employee_dao.insert(employee_req);

        PayoutTypeRequest payout_type_req = new PayoutTypeRequest();
        payout_type_req.setPayoutType("PayoutDAO FindAll Bonus");
        UUID payout_type_id = payout_type_dao.insert(payout_type_req);

        PayoutRequest req = new PayoutRequest();
        req.setEmployeeId(employee_id);
        req.setPayoutTypeId(payout_type_id);
        req.setDate(LocalDate.now());
        req.setValue(6000.0);
        payout_dao.insert(req);

        assert !payout_dao.findAll().isEmpty();
    }
}
