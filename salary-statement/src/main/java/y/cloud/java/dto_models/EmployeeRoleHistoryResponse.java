package y.cloud.java.dto_models;

import y.cloud.java.salary_statement_models.EmployeeRoleHistory;

import java.time.LocalDate;
import java.util.UUID;

public class EmployeeRoleHistoryResponse {
    private UUID employee_id;
    private UUID project_id;
    private String project_name;
    private UUID role_id;
    private String role_name;
    private LocalDate start_date;
    private LocalDate end_date;

    public EmployeeRoleHistoryResponse() {}
    public EmployeeRoleHistoryResponse(EmployeeRoleHistory erh) {
        this.employee_id = erh.getId().getEmployeeId();
        this.project_id = erh.getId().getProjectId();
        this.role_id = erh.getId().getRoleId();
        this.start_date = erh.getStartDate();
        this.end_date = erh.getEndDate();
    }

    public UUID getEmployeeId() { return employee_id; }
    public UUID getProjectId() { return project_id; }
    public String getProjectName() { return project_name; }
    public UUID getRoleId() { return role_id; }
    public String getRoleName() { return role_name; }
    public LocalDate getStartDate() { return start_date; }
    public LocalDate getEndDate() { return end_date; }

    public void setEmployeeId(UUID employee_id) {
        this.employee_id = employee_id;
    }
    public void setProjectId(UUID project_id) {
        this.project_id = project_id;
    }
    public void setProjectName(String project_name) { this.project_name = project_name; }
    public void setRoleId(UUID role_id) {
        this.role_id = role_id;
    }
    public void setRoleName(String role_name) { this.role_name = role_name; }
    public void setStartDate(LocalDate start_date) {
        this.start_date = start_date;
    }
    public void setEndDate(LocalDate end_date) {
        this.end_date = end_date;
    }
}
