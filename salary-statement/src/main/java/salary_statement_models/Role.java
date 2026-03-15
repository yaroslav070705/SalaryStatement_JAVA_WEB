package salary_statement_models;

import java.util.UUID;


public class Role {
    private UUID role_id;
    private String role_name;

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
}
