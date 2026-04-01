package y.cloud.java.salary_statement_models;

import y.cloud.java.dto_models.EmployeeRoleHistoryRequest;

import java.time.LocalDate;
import java.util.UUID;


public class EmployeeRoleHistory {
    private Employee employee_id;
    private Project project_id;
    private Role role_id;
    private LocalDate start_date;
    private LocalDate end_date;

    public EmployeeRoleHistory() {}
    public EmployeeRoleHistory(EmployeeRoleHistoryRequest req) {
        this.start_date = req.getStartDate();
        this.end_date = req.getEndDate();
    }

    public EmployeeRoleHistoryPK getId() {
        return new EmployeeRoleHistoryPK(employee_id.getId(), project_id.getId(), role_id.getId());
    }
    public void setId(Employee employee_id, Project project_id, Role role_id) {
        this.employee_id = employee_id;
        this.role_id = role_id;
        this.project_id = project_id;
    }

    public LocalDate getStartDate() {
        return start_date;
    }

    public void setStartDate(LocalDate start_date) {
        this.start_date =start_date;
    }

    public LocalDate getEndDate() {
        return end_date;
    }

    public void setEndDate(LocalDate end_date) {
        this.end_date = end_date;
    }
}
