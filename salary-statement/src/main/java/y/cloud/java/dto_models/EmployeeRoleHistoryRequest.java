package y.cloud.java.dto_models;

import java.time.LocalDate;
import java.util.UUID;

public class EmployeeRoleHistoryRequest {
    private UUID employee_id;
    private UUID project_id;
    private UUID role_id;
    private LocalDate start_date;
    private LocalDate end_date;

    public EmployeeRoleHistoryRequest() {}
    public EmployeeRoleHistoryRequest(UUID employee_id,
                                      UUID project_id,
                                      UUID role_id,
                                      LocalDate start_date,
                                      LocalDate end_date) {
        this.employee_id = employee_id;
        this.project_id = project_id;
        this.role_id = role_id;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public UUID getEmployeeId() { return employee_id; }
    public UUID getProjectId() { return project_id; }
    public UUID getRoleId() { return role_id; }
    public LocalDate getStartDate() { return start_date; }
    public LocalDate getEndDate() { return end_date; }

    public void setEmployeeId(UUID employee_id) {
        this.employee_id = employee_id;
    }
    public void setProjectId(UUID project_id) {
        this.project_id = project_id;
    }
    public void setRoleId(UUID role_id) {
        this.role_id = role_id;
    }
    public void setStartDate(LocalDate start_date) {
        this.start_date = start_date;
    }
    public void setEndDate(LocalDate end_date) {
        this.end_date = end_date;
    }
}
