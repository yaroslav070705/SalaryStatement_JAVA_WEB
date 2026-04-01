package y.cloud.java.salary_statement_models;

import java.util.UUID;
import java.io.Serializable;


public class ProjectSetupPK implements Serializable {
    private UUID employee_id;
    private UUID project_id;

    public ProjectSetupPK(){}
    public ProjectSetupPK(UUID employee_id, UUID project_id) {
        this.employee_id = employee_id;
        this.project_id = project_id;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ProjectSetupPK ps_obj){
            if (this == obj) {
                return true;
            }

            return ps_obj.employee_id == employee_id &&
                    ps_obj.project_id == project_id;
        }

        return false;
    }

    public int hashCode() {
        return employee_id.hashCode() + project_id.hashCode();
    }
}