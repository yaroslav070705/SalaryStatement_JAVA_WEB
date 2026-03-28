package salary_statement_models;

import dto_models.RoleRequest;

import java.util.UUID;


public class Role {
    private UUID role_id;
    private String role_name;

    public Role() {}
    public  Role(RoleRequest req) {
        this.role_name = getRoleName();
    }

    public UUID getId() {
        return role_id;
    }

    public boolean setId(UUID role_id) {
        if(role_id == null){
            return false;
        }

        this.role_id = role_id;

        return true;
    }

    public String getRoleName() {
        return role_name;
    }

    public void setRoleName(String role_name) {
        this.role_name = role_name;
    }
}
