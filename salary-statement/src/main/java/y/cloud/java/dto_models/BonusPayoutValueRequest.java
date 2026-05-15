package y.cloud.java.dto_models;

import java.util.UUID;

public class BonusPayoutValueRequest {
    private UUID payout_type_id;
    private Integer value;

    public UUID getPayoutTypeId() {
        return payout_type_id;
    }

    public Integer getValue() {
        return value;
    }

    public void setPayoutTypeId(UUID payout_type_id) {
        this.payout_type_id = payout_type_id;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
