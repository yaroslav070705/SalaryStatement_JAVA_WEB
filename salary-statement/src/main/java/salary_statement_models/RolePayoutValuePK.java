package salary_statement_models;

import java.util.UUID;
import java.io.Serializable;


public class RolePayoutValuePK implements Serializable {
    private UUID project_id;
    private UUID role_id;

    public RolePayoutValuePK() {}
    public RolePayoutValuePK(UUID project_id, UUID role_id) {
        this.project_id = project_id;
        this.role_id = role_id;
    }

    public boolean equals(Object obj) {
        if (obj instanceof RolePayoutValuePK rp_obj){
            if (this == obj) {
                return true;
            }

            return rp_obj.project_id == project_id &&
                    rp_obj.role_id == role_id;
        }

        return false;
    }

    public int hashCode() {
        return project_id.hashCode() + role_id.hashCode();
    }
}