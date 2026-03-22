package salary_statement_models;

import java.util.UUID;
import java.io.Serializable;


public class PayoutPK implements Serializable {
    private UUID employee_id;
    private UUID payout_type_id;

    public PayoutPK(){}
    public PayoutPK(UUID employee_id, UUID payout_type_id) {
        this.employee_id = employee_id;
        this.payout_type_id = payout_type_id;
    }

    public boolean equals(Object obj) {
        if (obj instanceof PayoutPK p_obj){
            if (this == obj) {
                return true;
            }

            return p_obj.employee_id == employee_id &&
                    p_obj.payout_type_id == payout_type_id;
        }

        return false;
    }

    public int hashCode() {
        return employee_id.hashCode() + payout_type_id.hashCode();
    }
}