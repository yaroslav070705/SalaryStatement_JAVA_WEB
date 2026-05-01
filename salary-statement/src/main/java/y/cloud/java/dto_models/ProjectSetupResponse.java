package y.cloud.java.dto_models;
import y.cloud.java.salary_statement_models.ProjectSetup;

import java.util.UUID;

public class ProjectSetupResponse {
    private UUID employee_id;
    private UUID project_id;
    private String project_name;
    private UUID role_id;
    private String role_name;

    public ProjectSetupResponse() {}

    public ProjectSetupResponse(ProjectSetup project_setup) {
        this.employee_id = project_setup.getId().getEmployeeId();
        this.project_id = project_setup.getId().getProjectId();
        this.role_id = project_setup.getRoleId();
    }

    public UUID getEmployeeId() { return employee_id; }
    public UUID getProjectId() {
        return project_id;
    }
    public String getProjectName() { return project_name; }
    public UUID getRoleId() {
        return role_id;
    }
    public String getRoleName() {return role_name;}

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
    public void setRoleName(String role_name) {this.role_name = role_name; }
}
