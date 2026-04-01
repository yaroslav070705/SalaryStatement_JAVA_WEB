package tests;

import y.cloud.java.dao_models.EmployeeDAO;
import y.cloud.java.dao_models.PostDAO;
import y.cloud.java.dto_models.EmployeeRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class EmployeeTest {
    @Autowired
    private EmployeeDAO employee_dao;
    @Autowired
    private PostDAO post_dao;

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
