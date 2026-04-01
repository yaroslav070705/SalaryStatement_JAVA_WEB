package tests;

import dao_models.EmployeeDAO;
import dao_models.PostDAO;
import dto_models.EmployeeRequest;

import org.testng.annotations.*;
import salary_statement_models.Employee;

import static org.testng.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class EmployeeTest {
    private EmployeeDAO employee_dao = new EmployeeDAO();
    private PostDAO post_dao = new PostDAO();

    @BeforeClass
    public void setUp() {

    }

    @Test
    public void addEmployeeTest() {
        EmployeeRequest req = new EmployeeRequest();
        req.setName("Иван");
        req.setSurname("Иванов");
        req.setMiddleName("Иванович");
        req.setBirthDate(LocalDate.of(1990, 5, 15));
        req.setWorkExperience(5);
        req.setFired(false);
        req.setPostId(post_dao.findByName("Developer").getId());

        UUID id = employee_dao.insert(req);

        assertNotNull(id);
    }

    public void updateEmployeeTest() {
        EmployeeRequest req = new EmployeeRequest();
        req.setName("Для");
        req.setSurname("Обновления");
        req.setMiddleName("Иванович");
        req.setBirthDate(LocalDate.of(1990, 5, 15));
        req.setWorkExperience(5);
        req.setFired(false);
        req.setPostId(post_dao.findByName("Developer").getId());
        employee_dao.insert(req);
    }

}
