package y.cloud.java.dto_models;

import y.cloud.java.salary_statement_models.RolePayoutValue;

import java.util.UUID;

public class RolePayoutValueResponse {
    private UUID project_id;
    private String project_name;
    private UUID role_id;
    private String role_name;
    private Double value;

    public RolePayoutValueResponse() {}
    public RolePayoutValueResponse(RolePayoutValue rpv) {
        this.role_id = rpv.getId().getRoleId();
        this.project_id = rpv.getId().getProjectId();
        this.value = rpv.getValue();
    }

    public UUID getProjectId() {
        return project_id;
    }
    public String getProjectName() { return project_name; }
    public UUID getRoleId() {
        return role_id;
    }
    public String getRoleName() { return role_name; }
    public Double getValue() {
        return value;
    }

    public void setProjectId(UUID project_id) {
        this.project_id = project_id;
    }
    public void setProjectName(String project_name) { this.project_name = project_name; }
    public void setRoleId(UUID role_id) {
        this.role_id = role_id;
    }
    public void setRoleName(String role_name) {this.role_name = role_name; }
    public void setValue(Double value) {
        this.value = value;
    }
}
