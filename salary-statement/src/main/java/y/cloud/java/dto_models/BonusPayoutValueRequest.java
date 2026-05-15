package y.cloud.java.dto_models;

import java.util.UUID;

public class BonusPayoutValueRequest {
    private UUID payout_type_id;
    private String payout_type;
    private Integer value;

    public UUID getPayoutTypeId() {
        return payout_type_id;
    }

    public String getPayoutType() {
        return payout_type;
    }

    public Integer getValue() {
        return value;
    }

    public void setPayoutTypeId(UUID payout_type_id) {
        this.payout_type_id = payout_type_id;
    }

    public void setPayoutType(String payout_type) {
        this.payout_type = payout_type;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
