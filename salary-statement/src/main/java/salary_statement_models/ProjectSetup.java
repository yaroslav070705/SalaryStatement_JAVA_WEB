package salary_statement_models;

import dto_models.ProjectSetupRequest;

import java.util.UUID;


public class ProjectSetup {
    private Employee employee_id;
    private Project project_id;
    private Role role_id;

    public UUID[] getId() {
        return new UUID[]{employee_id.getId(), project_id.getId()};
    }

    public void setId(Employee employee_id, Project project_id) {
        this.employee_id = employee_id;
        this.project_id = project_id;
    }

    public UUID getRoleId() {
        return role_id.getId();
    }

    public void setRoleId(Role role_id) {
        this.role_id = role_id;
    }
}
