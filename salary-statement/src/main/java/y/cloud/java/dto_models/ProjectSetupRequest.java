package y.cloud.java.dto_models;

import java.util.UUID;

public class ProjectSetupRequest {
    private UUID employee_id;
    private UUID project_id;
    private UUID role_id;

    public UUID getEmployeeId() { return employee_id; }
    public UUID getProjectId() {
        return project_id;
    }
    public UUID getRoleId() {
        return role_id;
    }

    public void setEmployeeId(UUID employee_id) {
        this.employee_id = employee_id;
    }
    public void setProjectId(UUID project_id) {
        this.project_id = project_id;
    }
    public void setRoleId(UUID role_id) {
        this.role_id = role_id;
    }
}
