package salary_statement_models;

import java.util.UUID;
import java.io.Serializable;

public class EmployeeRoleHistoryPK implements Serializable {
    private UUID employee_id;
    private UUID project_id;
    private UUID role_id;
}