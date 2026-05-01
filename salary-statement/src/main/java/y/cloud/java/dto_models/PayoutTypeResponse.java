package y.cloud.java.dto_models;

import y.cloud.java.salary_statement_models.PayoutType;

import java.util.UUID;

public class PayoutTypeResponse {
    private UUID payout_type_id;
    private String payout_type;

    public PayoutTypeResponse() {}
    public PayoutTypeResponse(PayoutType payout_type_obj) {
        this.payout_type_id = payout_type_obj.getId();
        this.payout_type = payout_type_obj.getPayoutType();
    }

    public UUID getPayoutTypeId() {
        return payout_type_id;
    }
    public String getPayoutType() {
        return payout_type;
    }

    public void setPayoutTypeId(UUID payout_type_id) { this.payout_type_id = payout_type_id; }
    public void setPayoutType(String payout_type) {
        this.payout_type = payout_type;
    }
}
