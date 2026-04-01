package y.cloud.java.salary_statement_models;

import java.util.UUID;
import java.io.Serializable;


public class EmployeeRoleHistoryPK implements Serializable {
    private UUID employee_id;
    private UUID project_id;
    private UUID role_id;

    public EmployeeRoleHistoryPK(){}
    public EmployeeRoleHistoryPK(UUID employee_id, UUID project_id, UUID role_id) {
        this.employee_id = employee_id;
        this.project_id = project_id;
        this.role_id = role_id;
    }

    public boolean equals(Object obj) {
        if (obj instanceof EmployeeRoleHistoryPK er_obj){
            if (this == obj) {
                return true;
            }

            return er_obj.employee_id == employee_id &&
                    er_obj.project_id == project_id &&
                    er_obj.role_id == role_id;
        }

        return false;
    }

    public int hashCode() {
        return employee_id.hashCode() + project_id.hashCode() + role_id.hashCode();
    }
}