package salary_statement_models;

import java.time.LocalDate;
import java.util.UUID;


public class EmployeeRoleHistory {
    private Employee employee_id;
    private Project project_id;
    private Role role_id;
    private LocalDate start_date;
    private LocalDate end_date;

    public UUID[] getId() {
        return new UUID[]{employee_id.getId(), project_id.getId(), role_id.getId()};
    }

    public LocalDate getStartDate() {
        return start_date;
    }

    public LocalDate getEndDate() {
        return end_date;
    }
}
