package y.cloud.java.dto_models;

import y.cloud.java.salary_statement_models.BonusPayoutValue;

import java.util.UUID;

public class BonusPayoutValueResponse {
    private UUID payout_type_id;
    private String payout_type;
    private Integer value;

    public BonusPayoutValueResponse() {}

    public BonusPayoutValueResponse(BonusPayoutValue bonus_payout_value) {
        this.payout_type_id = bonus_payout_value.getPayoutTypeId();
        this.value = bonus_payout_value.getValue();
    }

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
