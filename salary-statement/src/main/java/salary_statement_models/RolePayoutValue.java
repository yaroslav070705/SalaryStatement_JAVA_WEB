package salary_statement_models;

import java.util.UUID;


public class RolePayoutValue {
    private Project project_id;
    private Role role_id;
    private Double value;

    public UUID[] getId() {
        return new UUID[]{project_id.getId(), role_id.getId()};
    }

    public void setId(Project project_id, Role role_id) {
        this.project_id = project_id;
        this.role_id = role_id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
