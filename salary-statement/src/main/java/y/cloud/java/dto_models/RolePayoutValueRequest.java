package y.cloud.java.dto_models;

import java.util.UUID;

public class RolePayoutValueRequest {
    private UUID project_id;
    private UUID role_id;
    private Double value;

    public UUID getProjectId() {
        return project_id;
    }
    public UUID getRoleId() {
        return role_id;
    }
    public Double getValue() {
        return value;
    }

    public void setProjectId(UUID project_id) {
        this.project_id = project_id;
    }
    public void setRoleId(UUID role_id) {
        this.role_id = role_id;
    }
    public void setValue(Double value) {
        this.value = value;
    }
}
