package salary_statement_models;

import java.util.UUID;


public class PayoutType {
    private UUID payout_type_id;
    private String payout_type;

    public UUID getId() {
        return payout_type_id;
    }

    public String getPayoutType() {
        return payout_type;
    }
}
