package dto_models;

import java.util.UUID;

public class PayoutTypeRequest {
    private UUID payout_type_id;
    private String payout_type;

    public UUID getId() {
        return payout_type_id;
    }

    public void setId(UUID payout_type_id) {
        this.payout_type_id = payout_type_id;
    }

    public String getPayoutType() {
        return payout_type;
    }

    public void setPayoutType(String payout_type) {
        this.payout_type = payout_type;
    }
}
