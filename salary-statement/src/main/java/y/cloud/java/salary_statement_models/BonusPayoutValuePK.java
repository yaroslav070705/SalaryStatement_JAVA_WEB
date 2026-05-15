package y.cloud.java.salary_statement_models;

import java.io.Serializable;
import java.util.UUID;

public class BonusPayoutValuePK implements Serializable {
    private UUID payout_type_id;

    public BonusPayoutValuePK() {}

    public BonusPayoutValuePK(UUID payout_type_id) {
        this.payout_type_id = payout_type_id;
    }

    public UUID getPayoutTypeId() {
        return payout_type_id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BonusPayoutValuePK)) {
            return false;
        }

        BonusPayoutValuePK other = (BonusPayoutValuePK) obj;
        return payout_type_id != null && payout_type_id.equals(other.payout_type_id);
    }

    @Override
    public int hashCode() {
        return payout_type_id == null ? 0 : payout_type_id.hashCode();
    }
}
