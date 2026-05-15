package y.cloud.java.salary_statement_models;

import y.cloud.java.dto_models.BonusPayoutValueRequest;

import java.io.Serializable;
import java.util.UUID;

public class BonusPayoutValue implements Serializable {
    private UUID payout_type_id;
    private Integer value;

    public BonusPayoutValue() {}

    public BonusPayoutValue(BonusPayoutValueRequest req) {
        this.payout_type_id = req.getPayoutTypeId();
        this.value = req.getValue();
    }

    public UUID getPayoutTypeId() {
        return payout_type_id;
    }

    public void setPayoutTypeId(UUID payout_type_id) {
        this.payout_type_id = payout_type_id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
