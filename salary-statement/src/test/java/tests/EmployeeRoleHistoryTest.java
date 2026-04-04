package tests;

import y.cloud.java.App;
import y.cloud.java.dao_models.EmployeeDAO;
import y.cloud.java.dao_models.PostDAO;
import y.cloud.java.dto_models.EmployeeRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest(classes = App.class)
public class EmployeeRoleHistoryTest {
}
