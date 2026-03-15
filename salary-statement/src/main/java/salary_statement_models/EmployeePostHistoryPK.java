package salary_statement_models;

import java.util.UUID;
import java.io.Serializable;

public class EmployeePostHistoryPK implements Serializable {
    private UUID employee_id;
    private UUID post_id;

    public boolean equals(Object obj) {
        if (obj instanceof EmployeePostHistoryPK ep_obj){
            if (this == obj) {
                return true;
            }

            return ep_obj.employee_id == employee_id &&
                    ep_obj.post_id == post_id;
        }

        return false;
    }

    public int hashCode() {
        return employee_id.hashCode() + post_id.hashCode();
    }
}