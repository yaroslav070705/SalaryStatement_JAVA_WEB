package salary_statement_models;

import java.util.UUID;


public class RolePayoutValue {
    private Project project_id;
    private Role role_id;
    private Double value;

    public UUID[] getId() {
        return new UUID[]{project_id.getId(), role_id.getId()};
    }

    public boolean setId(UUID project_id, UUID role_id) {
        if (project_id == null || role_id==null){
            return false;
        }

        this.project_id.setId(project_id);
        this.role_id.setId(role_id);

        return true;
    }

    public Double getValue() {
        return value;
    }
}
