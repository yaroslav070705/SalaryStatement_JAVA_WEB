package y.cloud.java.dto_models;

import java.util.UUID;

public class RoleRequest {

    private UUID role_id;
    private String role_name;

    public UUID getRoleId() {
        return role_id;
    }
    public String getRoleName() {
        return role_name;
    }

    public void setRoleId(UUID role_id) {
        this.role_id = role_id;
    }
    public void setRoleName(String role_name) { this.role_name = role_name; }
}
