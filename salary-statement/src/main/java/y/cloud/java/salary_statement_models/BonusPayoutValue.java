package y.cloud.java.salary_statement_models;

import y.cloud.java.dto_models.BonusPayoutValueRequest;

import java.util.UUID;

public class BonusPayoutValue {
    private PayoutType payout_type_id;
    private int value;

    public BonusPayoutValue() {}

    public BonusPayoutValue(BonusPayoutValueRequest req) {
        this.value = req.getValue();
    }

    public UUID getPayoutTypeId() {
        return payout_type_id.getId();
    }

    public String getPayoutType() {
        return payout_type_id.getPayoutType();
    }

    public void setPayoutTypeId(PayoutType payout_type_id) {
        this.payout_type_id = payout_type_id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
